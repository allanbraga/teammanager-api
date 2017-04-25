angular.
module('core.employee').
factory('Employee', ['$resource',
    function($resource) {
        return $resource('http://localhost:8080/employees/:employId.json', {}, {
            query: {
                method: 'GET',
                params: {employId: 'employees'},
                isArray: true
            }
        });
    }
]);