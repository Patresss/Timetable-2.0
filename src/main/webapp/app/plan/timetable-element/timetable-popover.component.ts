import {Component, Input} from '@angular/core';
import {EventType, Timetable} from '../../entities/timetable';

@Component({
    selector: 'jhi-timetable-popover',
    templateUrl: './timetable-popover.html'
})
export class TimetableElementComponent {

    EventType = EventType;

    @Input()
    timetable: Timetable;

    @Input()
    timetableDate: Date;

    @Input()
    canModifyTimetable: boolean;

    goToEdit() {
        console.log('goToEdit')
    }

}
