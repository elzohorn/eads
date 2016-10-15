(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('PersonDeleteController',PersonDeleteController);

    PersonDeleteController.$inject = ['$uibModalInstance', 'entity', 'Person'];

    function PersonDeleteController($uibModalInstance, entity, Person) {
        var vm = this;

        vm.person = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Person.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
