new (class Control
{
    constructor() 
    {
        const root = d3.select('body').append('div')
        .style('width', '76vw')
        .style('height', '40vw')
        .attr('class', 'main')

        this.map = new CarbonMap(this, root);
        this.piechart = new Piechart(this, root);
        this.interaction = new Interaction(this, root);
        this.barchart = new Barchart(this, root);
    }
})()
