const urlParams = new URLSearchParams(window.location.search);

if (urlParams.get("appId")) {
    $("#affaires-title").text("Affaires directes par apporteur")
} else if (urlParams.get("appIdAll")) {
    $("#affaires-title").text("Toutes les affaires par apporteur")
}