/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TimetableTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PropertiesDetailComponent } from '../../../../../../main/webapp/app/entities/properties/properties-detail.component';
import { PropertiesService } from '../../../../../../main/webapp/app/entities/properties/properties.service';
import { Properties } from '../../../../../../main/webapp/app/entities/properties/properties.model';

describe('Component Tests', () => {

    describe('Properties Management Detail Component', () => {
        let comp: PropertiesDetailComponent;
        let fixture: ComponentFixture<PropertiesDetailComponent>;
        let service: PropertiesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TimetableTestModule],
                declarations: [PropertiesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PropertiesService,
                    JhiEventManager
                ]
            }).overrideTemplate(PropertiesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PropertiesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertiesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Properties(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.properties).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
