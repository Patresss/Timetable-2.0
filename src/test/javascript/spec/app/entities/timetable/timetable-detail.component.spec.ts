/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TimetableTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TimetableDetailComponent } from '../../../../../../main/webapp/app/entities/timetable/timetable-detail.component';
import { TimetableService } from '../../../../../../main/webapp/app/entities/timetable/timetable.service';
import { Timetable } from '../../../../../../main/webapp/app/entities/timetable/timetable.model';

describe('Component Tests', () => {

    describe('Timetable Management Detail Component', () => {
        let comp: TimetableDetailComponent;
        let fixture: ComponentFixture<TimetableDetailComponent>;
        let service: TimetableService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TimetableTestModule],
                declarations: [TimetableDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TimetableService,
                    JhiEventManager
                ]
            }).overrideTemplate(TimetableDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TimetableDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimetableService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Timetable(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.timetable).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
