$(document).ready(function () {
    $('#apporteurs-table').DataTable({
        processing: true,
        serverSide: true,
        ajax: '/CGL_M2_2022_war_exploded/apporteurs-data',
        columns: [
            { data: 'id' },
            { data: 'nom' },
            { data: 'prenom' },
            { data: 'affilie' },
            { data: 'totalCommissionsMCourant' },
            { data: 'totalCommissionsMM1' },
            { data: 'totalCommissionsMM2' },
            {
                data: null,
                render: function ( data, type, row ) {
                    return '<a class="btn btn-success" href="affaires?appId=' + data.id + '" role="button">Voir</a>';

                }
            }
        ],
    });
});