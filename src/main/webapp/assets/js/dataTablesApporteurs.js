$(document).ready(function () {
    $('#apporteurs-table').DataTable({
        processing: true,
        serverSide: true,
        searching: false,
        autoWidth: false,
        ajax: '/CGL_M2_2022_war/apporteurs-data',
        columns: [
            {
                data: 'id',
                render: (data, _type, row) => row['isDeleted'] ? `<div class="deleted-apporteur">${data}</div>` : data
            },
            {
                data: 'nom',
                render: (data, _type, row) => row['isDeleted'] ? `<div class="deleted-apporteur">${data}</div>` : data
            },
            {
                data: 'prenom',
                width: '200px',
                render: (data, _type, row) => row['isDeleted'] ? `<div class="deleted-apporteur">${data}</div>` : data
            },
            {
                data: 'affilie',
                render: function (data, _type, row) {
                    if (row['isDeleted'] || !data) return '<i class="bi bi-x-lg"></i>';
                    return '<i class="bi bi-check2"></i>';
                },
                width: "50px",
                orderable: true,
            },
            {
                data: 'totalCommissionsMCourant',
                width: "150px",
                render: (data, _type, row) => row['isDeleted'] ? `<div class="deleted-apporteur">${data}</div>` : data
            },
            {
                data: 'totalCommissionsMM1',
                width: "150px",
                render: (data, _type, row) => row['isDeleted'] ? `<div class="deleted-apporteur">${data}</div>` : data
            },
            {
                data: 'totalCommissionsMM2',
                width: "150px",
                render: (data, _type, row) => row['isDeleted'] ? `<div class="deleted-apporteur">${data}</div>` : data
            },
            {
                data: null,
                render: function (data, _type, row) {
                    if (row['isDeleted']) return '<div></div>';
                    return '<a class="btn btn-success" href="affaires?appId=' + data.id + '" role="button">Voir</a>';
                },
                width: "50px",
                orderable: false,
            },
            {
                data: null,
                render: function (data, _type, row) {
                    if (row['isDeleted']) return '<div></div>';
                    return '<a class="btn btn-success" href="affaires?appIdAll=' + data.id + '" role="button">Voir</a>';
                },
                width: "80px",
                orderable: false,
            },
            {
                data: null,
                render: function (data) {
                    if (!data.isDeleted) {
                        return '<div><a class="btn btn-primary" style="margin-right: 10px" href="add_apporteur?appId=' + data.id + '" role="button"><i class="bi bi-pencil"></i></a>' +
                               '<a class="btn btn-danger" href="delete_apporteur?appId=' + data.id + '" role="button"><i class="bi bi-trash3"></i></a></div>';
                    }
                    else {
                        return '<div class="deleted-apporteur">Supprimé</div>';
                    }
                },
                width: "125px",
                orderable: false,
            }
        ],
    });
});