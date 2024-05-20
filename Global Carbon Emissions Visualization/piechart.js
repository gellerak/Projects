class Piechart {
    constructor(con, root) {
        // Changed everything to use the "this" keyword so that it would work.
        // but if you do that you have to do like .then(data => {}) instead of .then(function(data)) 
        // aka use the => for functions !!
        this.con = con;
        this.root = root;

        this.width = 320;
        this.height = 320;
        this.radius = Math.min(this.width, this.height) / 2;

        this.div = this.root.append('div')
            .style('width', '32%')
            .style('height', '50%')
            .style('overflow', 'hidden')
            .attr('class', 'pie-chart-container');

        this.div.append('h3') // Using a heading for the label
            .text('Carbon Emissions By Year')
            .style('text-align', 'center')
            .style('font-size', '20px')
            .style('margin-bottom', '10px');

        this.svg = this.div.append('svg')
            .attr('width', '100%')
            .attr('height', '100%')
            .append("g")
            // .attr("transform", `translate(${this.width / 2}, ${this.height / 2})`);

        // use these for interactions
        this.countryChoice = "WORLD";
        this.sectorChoice = "Power";
        this.seasonChoice = "All";

        // this reloads the view. basically put all your code for your visual in its own function
        this.renderPieChart(); 
    }

    // this updates the choices (used in interaction.js)
    // you wanna make sure you re-render the view after so it updates!!
    updateChoices(sector, country, season) 
    {
        this.countryChoice = country;
        this.sectorChoice = sector;
        this.seasonChoice = season;

        this.renderPieChart();
    }

    // again this basically holds all of the code to generate your view
    renderPieChart()
    {
        this.svg.selectAll("*").remove(); // IMPORTANT: this removes the old view so a new one can be replaced!!
        
        d3.csv("dataset.csv").then(data => {
            // filtering the view of the data; makes an array ("dataArray") that only contains data from the og array with the 
            // country choice (an example is "WORLD") & sector choice within the span of the chosen season for years 2019-2022
            const dataArray = data.filter(d => 
            {
                return d.sector == this.sectorChoice && d.country == this.countryChoice
                    && checkSeasonDates(d.date, this.seasonChoice) && d.date.split('/')[2] != "2023";
            });

            // getting average of data from date choice; you'll only want one value for each year, so this is getting the
            // average of data from each year from "dataArray"
            let avgData = [];
            dataArray.forEach(function (c) // this adds all values together for each year
            {
                let index = avgData.findIndex(obj => obj.date.split('/')[2] === c.date.split('/')[2]);

                if (index !== -1)
                {
                    avgData[index].value = +avgData[index].value + +c.value;
                }
                else
                {
                    avgData.push(c);
                }
            })

            avgData.forEach(d => {
                d.year = d.date.split('/')[2]; // Split the date and get the year
            });

            const pie = d3.pie()
                .value(d => d.value)(avgData); 

            const arc = d3.arc()
                .innerRadius(0)
                .outerRadius(this.radius);
            
            const color = d3.scaleOrdinal()
                .domain(avgData.map(d => d.year)) 
                .range(d3.schemeTableau10);

            const divBoundingRect = this.div.node().getBoundingClientRect();
            const centerX = divBoundingRect.width / 2;
            const centerY = divBoundingRect.height / 2;

            this.svg.attr("transform", `translate(${centerX}, ${centerY})`);

            this.svg.selectAll("path")
                .data(pie)
                .enter()
                .append("path")
                .attr("d", arc)
                .attr('fill', d => color(d.data.year)) // Use 'year' for coloring
                .attr("stroke", "white")
                .style("stroke-width", "2px")
                .style("opacity", 0.8);

            this.svg.selectAll("text")
                .data(pie)
                .enter()
                .append("text")
                .attr("transform", d => `translate(${arc.centroid(d)})`)
                .attr("dy", "5px")
                .attr("text-anchor", "middle")
                .text(d => `${d.data.year}: ${d.data.value.toFixed(2)}`) // Display year and value
                .style("fill", "white");

            
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
