let dataPoints= [];

for (var i = 0; i < listTrainer.length; i++) {
    dataPoints.push({label:listTrainer[i], y:listJumlahTraining[i]})
}

var trainer = {
    title: {
        text: "Training Operation Staff"
    },
    data: [{
        type: "pie",
        startAngle: 45,
        showInLegend: "true",
        legendText: "{label}",
        indexLabel: "{label} ({y})",
        yValueFormatString:"#,##0.#"%"",
        dataPoints
    }]
};