'use strict';

describe('Controller Tests', function() {

    describe('Podcast Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPodcast, MockPerson, MockChannel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPodcast = jasmine.createSpy('MockPodcast');
            MockPerson = jasmine.createSpy('MockPerson');
            MockChannel = jasmine.createSpy('MockChannel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Podcast': MockPodcast,
                'Person': MockPerson,
                'Channel': MockChannel
            };
            createController = function() {
                $injector.get('$controller')("PodcastDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'modulo04Practica02JhipsterApp:podcastUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
