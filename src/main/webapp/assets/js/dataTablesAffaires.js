$(document).ready(function () {
    $('#affaires-table').DataTable({
        processing: true,
        serverSide: true,
        searching: false,
        autoWidth: false,
        ajax: '/CGL_M2_2022_war/affaires-data',
        columns: [
            { data: 'id' },
            {
                data: 'date',
                render: function (data) {
                    return new Date(data).toLocaleDateString();
                }
            },
            { data: 'commissionGlobale', width: "150px" },
            {
                data: 'apporteur',
                render: function ( data, type, row ) {
                    return data.prenom + ' ' + data.nom;
                },
                orderable: false,
            },
            {
                data: 'commissions',
                render: function ( data, type, row ) {
                    console.log({ data })
                    return;
                },
                width: "50px",
                orderable: false,
            },
            {
                data: null,
                render: function ( data, type, row ) {
                    // Change routes :)
                    return '<a class="btn btn-primary" href="add_affaire?appId=' + data.id + '" role="button">Modifier</a>' +
                        '<a class="btn btn-danger" href="add_affaire?appId=' + data.id + '" role="button" style="margin-left: 2px">Supprimer</a>';
                },
                width: "220px",
                orderable: false,
            },
        ],
    });
});