let dataPoints = [];

for (var i = 0; i < listBulan.length; i++) {
    dataPoints.push({x:new Date(parseInt(listTahun[i], 10), parseInt(listBulan[i], 10)), y:listJumlahTraining[i]});
}

var training = {
    animationEnabled: true,
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
    data: [{
        yValueFormatString: "",
        xValueFormatString: "MMMM YYYY",
        type: "spline",
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
