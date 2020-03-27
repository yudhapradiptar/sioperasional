let dataPoints= [];

for (var i = 0; i < listNilai.length; i++) {
    dataPoints.push({label:listTrainer[i], y:listNilai[i]})
}

var nilai = {
    title: {
        text: "Nilai Feedback Customer"
    },
    data: [
        {
            // Change type to "doughnut", "line", "splineArea", etc.
            type: "column",
            dataPoints
        }
    ]
};

