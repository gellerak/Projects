class Interaction {
    constructor(con, root) {
        this.con = con;
        this.root = root;
        
        // Initialize variables
        this.dateChoice = "2019";
        this.countryChoice = "WORLD";
        this.sectorChoice = "Power";
        this.seasonChoice = "All";

        const div = root.append('div')
            .attr('class', 'interaction-controls')
            .style('width', '18%')
            .style('height', '50%');

        div.append('h3')
            .text('Interactions')
            .style('font-size', '30px')
            .style('text-align', 'center')
            .style('margin-bottom', '10px');

        // Dropdowns for selecting choices
        const selectCountry = div.append('select')
            .attr('class', 'custom_dropdown')
            .on('change', (event) => this.countryChoice = event.target.value);

        const selectDate = div.append('select')
            .attr('class', 'custom_dropdown')
            .on('change', (event) => this.dateChoice = event.target.value);

        const selectSeason = div.append('select')
            .attr('class', 'custom_dropdown')
            .on('change', (event) => this.seasonChoice = event.target.value);
        
        const selectSector = div.append('select')
            .attr('class', 'custom_dropdown')
            .on('change', (event) => this.sectorChoice = event.target.value);

        // Populate dropdown options
        selectCountry.selectAll('option')
            .data(['WORLD', 'Brazil', 'China', 'EU27 & UK', 'France', 'Germany', 'India', 'Italy',
                    'Japan', 'ROW', 'Russia', 'Spain', 'UK', 'US'])
            .enter()
            .append('option')
            .text(d => d);

        selectDate.selectAll('option')
            .data(['2019', '2020', '2021', '2022'])
            .enter()
            .append('option')
            .text(d => d);

        selectSeason.selectAll('option')
            .data(['All', 'Spring 🌼', 'Summer ☀️', 'Fall 🍁', 'Winter ❄️'])
            .enter()
            .append('option')
            .text(d => d);
        
        selectSector.selectAll('option')
            .data(['Power', 'Industry', 'Ground Transport', 'Residential', 'Domestic Aviation',
                   'International Aviation'])
            .enter()
            .append('option')
            .text(d => d);

        div.append('button')
            .text('Update Data')
            .attr('class', 'custom_button')
            .on('click', () => this.updateData()); // Call updateData when the button is clicked
    }
    
    updateData() {
        // Dispatch custom event with selected choices
        const event = new CustomEvent('updateChoices', {
            detail: {
                country: this.countryChoice,
                date: this.dateChoice,
                season: this.seasonChoice,
                sector: this.sectorChoice
            }
        });

        this.con.map.updateChoices(this.sectorChoice, this.dateChoice, this.seasonChoice);
        this.con.piechart.updateChoices(this.sectorChoice, this.countryChoice, this.seasonChoice);
        this.con.barchart.updateChoices(this.sectorChoice, this.dateChoice, this.seasonChoice, this.countryChoice);
    }
}
