
var tax_data = [
                 { "period": "2011 Q3", "licensed": 3407, "sorned": 660 },
                 { "period": "2011 Q2", "licensed": 3351, "sorned": 629 },
                 { "period": "2011 Q1", "licensed": 3269, "sorned": 618 },
                 { "period": "2010 Q4", "licensed": 3246, "sorned": 661 },
                 { "period": "2009 Q4", "licensed": 3171, "sorned": 676 },
                 { "period": "2008 Q4", "licensed": 3155, "sorned": 681 },
                 { "period": "2007 Q4", "licensed": 3226, "sorned": 620 },
                 { "period": "2006 Q4", "licensed": 3245, "sorned": null },
                 { "period": "2005 Q4", "licensed": 3289, "sorned": null }
];


var InitiateBarChart = function () {
    return {
        init: function () {
            Morris.Bar({
                element: 'bar-chart',
                data: [
                  { y: '2006', a: 100, b: 90, c:80 },
                  { y: '2007', a: 75, b: 65 , c:25},
                  { y: '2008', a: 50, b: 40 , c:90},
                  { y: '2009', a: 75, b: 65 , c:15},
                  { y: '2010', a: 50, b: 40 , c:50},
                  { y: '2011', a: 75, b: 65 ,c:10},
                  { y: '2012', a: 100, b: 90 ,c:90}
                ],
                xkey: 'y',
                ykeys: ['a', 'b', 'c'],
                labels: ['Series A', 'Series B', 'Series C'],
                hideHover: 'auto',
                barColors: [themeprimary, themesecondary, themethirdcolor]
            });
        }
    };
}();

var InitiateLineChart = function () {
    return {
        init: function () {
            Morris.Line({
                element: 'line-chart',
                data: tax_data,
                xkey: 'period',
                ykeys: ['licensed', 'sorned'],
                labels: ['Licensed', 'Off the road'],
                lineColors: [themeprimary, themethirdcolor]
            });

        }
    };
}();


var InitiateDonutChart = function () {
    return {
        init: function () {
            Morris.Donut({
                element: 'donut-chart',
                data: [
                  { label: 'IOS', value: 40 , },
                  { label: 'Win', value: 30 },
                  { label: 'Android', value: 25 },
                  { label: 'Java', value: 5 }
                ],
                colors: [themeprimary, themesecondary, themethirdcolor, themefourthcolor],
                formatter: function (y) { return y + "%" }
            });
        }
    };
}();
