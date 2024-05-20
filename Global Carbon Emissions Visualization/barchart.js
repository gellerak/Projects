class Barchart {
    constructor(con, root) {
        // Changed everything to use the "this" keyword so that it would work.
        // but if you do that you have to do like .then(data => {}) instead of .then(function(data)) 
        // aka use the => for functions !!
        this.con = con;
        this.root = root;
        
        this.margin = { top: 10, right: 25, bottom: 80, left: 50};
        
        
        this.div = this.root.append('div')
            .style('width', '50%')
            .style('height', '50%')
            .style('overflow', 'hidden')
            .attr('class', 'bar-chart-container')
            .attr('fill', 'black');
        
        this.svg = this.div.append('svg')
            .attr('width', '100%')
            .attr('height', '100%')
            .append("g")
            .attr("transform", `translate(${this.margin.left}, ${this.margin.top})`);

        // for interactions - this is what you wanna update!!
        this.countryChoice = "WORLD";
        this.dateChoice = "2019";
        this.seasonChoice = "All";
        this.sectorChoice = "Power";

        // this reloads the view. basically put all your code for your visual in its own function
        this.renderBarChart();

        window.addEventListener('resize', () => 
        {
            this.renderBarChart();
        });
    }

    // this updates the choices (used in interaction.js)
    // you wanna make sure you re-render the view after so it updates!!
    updateChoices(sector, date, season, country) 
    {
        this.countryChoice = country;
        this.dateChoice = date;
        this.seasonChoice = season;
        this.sectorChoice = sector;

        this.renderBarChart();
    }

    // again this basically holds all of the code to generate your view
    renderBarChart()
    {
        this.div.selectAll("*").remove(); // IMPORTANT: this removes the old view so a new one can be replaced!!

        this.svg = this.div.append('svg')
            .attr('width', '100%')
            .attr('height', '100%')
            .append("g")
            .attr("transform", `translate(${this.margin.left}, ${this.margin.top})`);

        this.svg.append("text")
            .attr("x", (this.div.node().getBoundingClientRect().width / 2) - this.margin.left) // Centering the label
            .attr("y", 20) 
            .attr("text-anchor", "middle")
            .style("font-size", "20px") 
            .text("Carbon Emissions By Sector");

        const width = this.div.node().getBoundingClientRect().width - this.margin.left - this.margin.right;
        const height = this.div.node().getBoundingClientRect().height - this.margin.top - this.margin.bottom;


        d3.csv("dataset.csv").then((data) => {
            // filtering the view of the data; makes an array ("dataArray") that only contains data from the og array with the 
            // country choice (an example is "WORLD") within the span of the chosen year/season
            const dataArray = data.filter(d => 
            {
                return d.country == this.countryChoice && d.date.split('/')[2] == this.dateChoice 
                    && checkSeasonDates(d.date, this.seasonChoice);
            });

            // getting average of data from date choice; you'll only want one value for each sector, so this is getting the
            // average of data from each sector from "dataArray"
            let avgData = [];
            dataArray.forEach(function (c) // this adds all values together for each sector
            {
                let index = avgData.findIndex(obj => obj.sector === c.sector);

                if (index !== -1)
                {
                    avgData[index].value = +avgData[index].value + +c.value;
                }
                else
                {
                    avgData.push(c);
                }
            })

            console.log("BARCHART");
            console.log(avgData);

            // X axis
            const x = d3.scaleBand()
                .range([0, width])
                .domain(avgData.map(d => d.sector))
                .padding(0.2);

            this.svg.append("g")
                .attr("transform", `translate(0, ${height})`)
                .call(d3.axisBottom(x))
                .selectAll("text")
                    .attr("transform", "translate(-10,0)rotate(-30)")
                    .style("text-anchor", "end");

            // Y axis
            const y = d3.scaleLinear()
                .domain([0, d3.max(avgData, d => d.value)])
                .range([height, 0]);
            this.svg.append("g")
                .call(d3.axisLeft(y));

            // Color Scale
            const color = d3.scaleOrdinal()
                            .domain(avgData.map(d => d.sector))
                            .range(d3.schemeCategory10)

            // Show bars
            this.svg.selectAll("bar")
                .data(avgData)
                .enter()
                .append("rect")
                    .attr("x", d => x(d.sector))
                    .attr("y", d => y(d.value))
                    .attr("height", d => height - y(d.value))
                    .attr("width", x.bandwidth())
                    .attr("fill", d => color(d.sector))
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
