
function onClickAddFlight() {
    let flightNo = document.querySelector('form input[name="flight-no"]').value;
    let origin = document.querySelector('form input[name="from"]').value;
    let destination = document.querySelector('form input[name="to"]').value;
    if (validateInput(flightNo, origin, destination)) {
        alert("Success!");
    }
}
function validateInput(flightNo, origin, destination) {
    let flightNoRegex = new RegExp(/^[A-Z0-9]{2}\d{3,4}$/);
    let airportCodeRegex = new RegExp(/^[A-Z]{3}$/);
    if (!flightNoRegex.test(flightNo)) {
        alert("Invalid Flight No.");
        return false;
    }
    if (!airportCodeRegex.test(origin)) {
        alert("Invalid origin airport code.");
        return false;
    }
    if (!airportCodeRegex.test(destination)) {
        alert("Invalid destination airport code.");
        return false;
    }
    return true
}
function initial() {
    let year = document.getElementById("year");
    year.innerHTML = "";
    year.options.add(new Option("--", null));
    for (let i = 2000; i <= 2020; i++) {
        year.options.add(new Option(i, i));
    }
}

