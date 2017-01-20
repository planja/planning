/**
 * Created by ShchykalauM on 20.01.2017.
 */

var db = {

    loadData: function (filter) {
        return $.grep(this.clients, function (client) {
            return (!filter.Name || client.Name.indexOf(filter.Name) > -1)
                && (!filter.Age || client.Age === filter.Age)
                && (!filter.Address || client.Address.indexOf(filter.Address) > -1)
                && (filter.Married === undefined || client.Married === filter.Married);
        });
    },

    insertItem: function (insertingClient) {
        this.clients.push(insertingClient);
    },

    updateItem: function (updatingClient) {
    },

    deleteItem: function (deletingClient) {
        var clientIndex = $.inArray(deletingClient, this.clients);
        this.clients.splice(clientIndex, 1);
    }

};

window.db = db;

db.countries = [
    {Name: "", Id: 0},
    {Name: "United States", Id: 1},
    {Name: "Canada", Id: 2},
    {Name: "United Kingdom", Id: 3},
    {Name: "France", Id: 4},
    {Name: "Brazil", Id: 5},
    {Name: "China", Id: 6},
    {Name: "Russia", Id: 7}
];

db.clients = [
    {
        "Name": "Otto Clay",
        "Age": 61,
        "Country": 6,
        "Address": "Ap #897-1459 Quam Avenue",
        "Married": false
    },
    {
        "Name": "Connor Johnston",
        "Age": 73,
        "Country": 7,
        "Address": "Ap #370-4647 Dis Av.",
        "Married": false
    },
    {
        "Name": "Lacey Hess",
        "Age": 29,
        "Country": 7,
        "Address": "Ap #365-8835 Integer St.",
        "Married": false
    }
];

$("#jsGrid").jsGrid({
    height: 300,
    width: "100%",

    filtering: true,
    editing: true,
    sorting: true,
    paging: true,
    autoload: true,
    inserting:true,

    pageSize: 15,
    pageButtonCount: 5,

    deleteConfirm: "Do you really want to delete the client?",

    controller: db,

    fields: [
        {name: "Name", type: "text", width: 150},
        {name: "Age", type: "number", width: 50},
        {name: "Address", type: "text", width: 200},
        {
            name: "Country",
            type: "select",
            items: db.countries,
            valueField: "Id",
            textField: "Name",
            filterValue: function () {
                return this.items[this.filterControl.val()][this.textField];
            }
        },
        {name: "Married", type: "checkbox", title: "Is Married", sorting: false},
        {type: "control"}
    ]
});
