(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('ChannelDeleteController',ChannelDeleteController);

    ChannelDeleteController.$inject = ['$uibModalInstance', 'entity', 'Channel'];

    function ChannelDeleteController($uibModalInstance, entity, Channel) {
        var vm = this;

        vm.channel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Channel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
