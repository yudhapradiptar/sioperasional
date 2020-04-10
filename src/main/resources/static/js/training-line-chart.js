let dataPoints = [];

for (var i = 0; i < listBulan.length; i++) {
    dataPoints.push({x:new Date(parseInt(listTahun[i], 10), parseInt(listBulan[i], 10)), y:listJumlahTraining[i]});
}
console.log(dataPoints);

var training = {
    animationEnabled: true,
    theme: "light2",
    title:{
        text: "Training 12 Bulan Terakhir"
    },
    axisX: {
        valueFormatString: "MMM YYYY"
    },
    axisY: {
        title: "Jumlah Training",
        prefix: "",
        includeZero: false
    },
    legend:{
        cursor:"pointer",
        verticalAlign: "bottom",
        horizontalAlign: "left",
        dockInsidePlotArea: true,
        itemclick: toogleDataSeries
    },
    data: [{
        type: "line",
        showInLegend: true,
        name: "Projected Sales",
        markerType: "square",
        xValueFormatString: "MMMM YYYY",
        color: "#F08080",
        yValueFormatString: "",
        dataPoints
    }]
};

function toogleDataSeries(e){
    if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
        e.dataSeries.visible = false;
    } else{
        e.dataSeries.visible = true;
    }
    e.chart.render();
}
