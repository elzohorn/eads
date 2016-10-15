(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('PodcastDeleteController',PodcastDeleteController);

    PodcastDeleteController.$inject = ['$uibModalInstance', 'entity', 'Podcast'];

    function PodcastDeleteController($uibModalInstance, entity, Podcast) {
        var vm = this;

        vm.podcast = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Podcast.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
