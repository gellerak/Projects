class CarbonMap
{
    constructor(con, root)
    {
        // TODO: //
        // hover over interaction

        // Changed everything to use the "this" keyword so that it would work.
        // but if you do that you have to do like .then(data => {}) instead of .then(function(data)) 
        // aka use the => for functions !!
        this.con = con;
        this.root = root;

        this.div = this.root.append('div')
            .style('width','50%')
            .style('height','100%')
            .style('overflow', 'hidden')
            .attr('fill', 'black');
        
        this.svg = this.div.append('svg')
            .attr('width', '99%')
            .attr('height', '120%');

        // use these for interactions
        this.sectorChoice = "Power";
        this.dateChoice = "2019"; // DD/MM/YYYY
        this.seasonChoice = "Summer";

        // this reloads the view. basically put all your code for your visual in its own function
        this.renderCarbonMap(); 

        // reloads view if browser window size is changed. kinda finicky
        window.addEventListener('resize', () => 
        {
            this.renderCarbonMap();
        });
    }

    // this updates the choices (used in interaction.js)
    // you wanna make sure you re-render the view after so it updates!!
    updateChoices(sector, date, season) 
    {
        this.sectorChoice = sector;
        this.dateChoice = date;
        this.seasonChoice = season;

        this.renderCarbonMap();
    }

    // again this basically holds all of the code to generate your view
    renderCarbonMap()
    {
        this.svg.selectAll("*").remove(); // IMPORTANT: this removes the old view so a new one can be replaced!!

        // Add a label at the top of the SVG
        this.svg.append("text")
            .attr("x", this.div.node().getBoundingClientRect().width / 2)
            .attr("y", 30)
            .attr("text-anchor", "middle")
            .style("font-size", "30px")
            .text("Carbon Emissions By Country");

        // sizing stuff
        const width = this.svg.node().getBoundingClientRect().width;
        const height = this.svg.node().getBoundingClientRect().height;

        d3.json("countries.geojson")
        .then(world =>
        {
            const projection = d3.geoMercator()
                .fitSize([width, height], world);
            const path = d3.geoPath().projection(projection);

            // some of the country names are different than my map data. so im renaming them
            const countryMap = 
            {
                "United States of America": "US",
                "United Kingdom": "UK"
            }

            world.features.forEach(function(d) 
            {
                if (countryMap[d.properties.ADMIN]) 
                {
                    d.properties.ADMIN = countryMap[d.properties.ADMIN];
                }
            });

            d3.csv('dataset.csv')
            .then(data => 
            {
                let eu27Array = ["Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus", "Czech Republic", "Denmark",
                    "Estonia", "Finland", "Greece", "Hungary", "Ireland", "Latvia", "Lithuania",
                    "Luxembourg", "Malta", "Netherlands", "Poland", "Portugal", "Romania",
                    "Slovakia", "Slovenia", "Sweden"];

                // filtering the view
                let dataArray = data.filter(d => 
                {
                    return d.sector == this.sectorChoice && d.country != "WORLD" && d.country != "ROW" 
                        && d.date.split('/')[2] == this.dateChoice && checkSeasonDates(d.date, this.seasonChoice);
                });

                // getting average of data from date choice
                let avgData = [];
                dataArray.forEach(function (c)
                {
                    let index = avgData.findIndex(obj => obj.country === c.country);

                    if (index !== -1)
                    {
                        avgData[index].value = +avgData[index].value + +c.value;
                    }
                    else
                    {
                        avgData.push(c);
                    }
                })

                console.log(avgData);

                // min/max calculations for colorscale
                let max = d3.max(avgData, function (d) 
                {
                    return +d.value;
                })

                let min = d3.min(avgData, function (d) 
                {
                    return +d.value;
                })

                console.log(min);
                console.log(max);

                let colorRange = ['#CBC3E3', '#702963'];

                let colorScale = d3.scaleLinear()
                    .domain([min, max])
                    .range(colorRange); 

                // map creation
                this.svg.selectAll("path")
                    .data(world.features)
                    .enter()
                    .append("path")
                    .attr("d", path)
                    .style("fill", function (d) 
                    {
                        let countryData = avgData.find(function (c) 
                        {
                            return c.country === d.properties.ADMIN;
                        })

                        let eu27Data = avgData.find(function (e)
                        {
                            return e.country === "EU27 & UK";
                        })

                        if (countryData)
                        {
                            return colorScale(countryData.value);
                        }
                        else if (eu27Array.includes(d.properties.ADMIN))
                        {
                            console.log(eu27Data.value);
                            return colorScale(eu27Data.value);
                        }
                        else
                        {
                            return "lightgrey";
                        }
                    })
                    .style("stroke", "black");
            });
        });
    }
}

// this helps with filtering data pls dont touch 
function checkSeasonDates(date, seasonChoice)
{
    if (seasonChoice == "Winter ❄️")
    {
        return date.split('/')[1] == "12" || date.split('/')[1] == "01" || date.split('/')[1] == "02";
    }
    else if (seasonChoice == "Spring 🌼")
    {
        return date.split('/')[1] == "03" || date.split('/')[1] == "04" || date.split('/')[1] == "05";
    }
    else if (seasonChoice == "Summer ☀️")
    {
        return date.split('/')[1] == "06" || date.split('/')[1] == "07" || date.split('/')[1] == "08";
    }
    else if (seasonChoice == "Fall 🍁")
    {
        return date.split('/')[1] == "09" || date.split('/')[1] == "10" || date.split('/')[1] == "11";
    }
    else
    {
        return true;
    }
}
