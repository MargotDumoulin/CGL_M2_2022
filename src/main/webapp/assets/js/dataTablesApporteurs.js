$(document).ready(function () {
    $('#apporteurs-table').DataTable({
        processing: true,
        serverSide: true,
        searching: false,
        autoWidth: false,
        ajax: '/CGL_M2_2022_war_exploded/apporteurs-data',
        columns: [
            { data: 'id' },
            { data: 'nom' },
            { data: 'prenom' },
            { data: 'affilie' },
            { data: 'totalCommissionsMCourant', width: "150px" },
            { data: 'totalCommissionsMM1', width: "150px" },
            { data: 'totalCommissionsMM2', width: "150px" },
            {
                data: null,
                render: function ( data, type, row ) {
                    return '<a class="btn btn-success" href="affaires?appId=' + data.id + '" role="button">Voir</a>';
                },
                width: "50px"
            }
        ],
    });
});