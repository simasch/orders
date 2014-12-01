var now = new Date().getTime();
var page_load_time = now - performance.timing.navigationStart;

window.document.getElementById("timing").innerHTML = "Request took " + page_load_time + " ms";