window.onload = function () {

//Better to construct options first and then pass it as a parameter
    var options = {
        title: {
            text: "Column Chart in jQuery CanvasJS"
        },
        data: [
            {
                // Change type to "doughnut", "line", "splineArea", etc.
                type: "column",
                dataPoints: [
                    { label: "apple",  y: 10  },
                    { label: "orange", y: 15  },
                    { label: "banana", y: 25  },
                    { label: "mango",  y: 30  },
                    { label: "grape",  y: 28  }
                ]
            }
        ]
    };

    $("#chartContainer").CanvasJSChart(options);
}