$(document).ready(function () {
    $('#apporteurs-table').DataTable({
        processing: true,
        serverSide: true,
        searching: false,
        autoWidth: false,
        ajax: '/CGL_M2_2022_war/apporteurs-data',
        columns: [
            { data: 'id' },
            { data: 'nom' },
            {
                data: 'prenom',
                width: '200px',
            },
            {
                data: 'affilie',
                render: function ( data ) {
                    return data
                        ? '<i class="bi bi-check2"></i>'
                        : '<i class="bi bi-x-lg"></i>'
                },
                width: "50px",
                orderable: true,
            },
            { data: 'totalCommissionsMCourant', width: "150px" },
            { data: 'totalCommissionsMM1', width: "150px" },
            { data: 'totalCommissionsMM2', width: "150px" },
            {
                data: null,
                render: function ( data ) {
                    return '<a class="btn btn-success" href="affaires?appId=' + data.id + '" role="button">Voir</a>';
                },
                width: "50px",
                orderable: false,
            },
            {
                data: null,
                render: function ( data ) {
                    return '<a class="btn btn-success" href="affaires?appIdAll=' + data.id + '" role="button">Voir</a>';
                },
                width: "80px",
                orderable: false,
            },
            {
                data: null,
                render: function ( data ) {
                    if (!data.isDeleted) {
                        return '<div><a class="btn btn-primary" href="add_apporteur?appId=' + data.id + '" role="button"><i class="bi bi-pencil"></i></a></div>' +
                               '<div><a class="btn btn-danger" href="delete_apporteur?appId=' + data.id + '" role="button"><i class="bi bi-trash3"></i></a></div>';
                    }
                    else {
                        return '<i>Supprimé</i>';
                    }
                },
                width: "50px",
                orderable: false,
            }
        ],
    });
});