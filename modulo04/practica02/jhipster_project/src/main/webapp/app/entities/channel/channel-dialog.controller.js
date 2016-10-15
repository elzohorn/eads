(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .controller('ChannelDialogController', ChannelDialogController);

    ChannelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Channel', 'Person', 'Podcast', 'Subscription'];

    function ChannelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Channel, Person, Podcast, Subscription) {
        var vm = this;

        vm.channel = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query();
        vm.podcasts = Podcast.query();
        vm.subscriptions = Subscription.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.channel.id !== null) {
                Channel.update(vm.channel, onSaveSuccess, onSaveError);
            } else {
                Channel.save(vm.channel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('modulo04Practica02JhipsterApp:channelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
