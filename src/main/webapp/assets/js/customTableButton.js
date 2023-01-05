$('.collapse').on('hide.bs.collapse', function (event) {
    const icon = $('#i' + event.target.id);
    icon.addClass('bi-chevron-down');
    icon.removeClass('bi-chevron-up');
})
$('.collapse').on('show.bs.collapse', function (event) {
    const icon = $('#i' + event.target.id);
    icon.addClass('bi-chevron-up');
    icon.removeClass('bi-chevron-down');
})