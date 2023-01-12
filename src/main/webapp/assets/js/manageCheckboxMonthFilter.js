
const url = new URL(window.location.href);
const checkbox = $("#affaires-filter-checkbox-month");

if (url.searchParams.get('filterByMonth') === "true") {
    checkbox.prop( "checked", true );
}
checkbox.click((event) => {
    if (event.target.checked) {
        url.searchParams.set('filterByMonth', true);
    } else {
        url.searchParams.set('filterByMonth', false);
    }

    window.location.href = url.toString();
})

