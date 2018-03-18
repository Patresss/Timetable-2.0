import {Component, HostListener, Input} from '@angular/core';
import {EventType, Timetable} from '../../entities/timetable';

@Component({
    selector: 'jhi-timetable-popover',
    templateUrl: './timetable-popover.html',
    styleUrls: ['./timetable-popover.component.scss'],
})
export class TimetableElementComponent {

    EventType = EventType;

    @Input()
    timetable: Timetable;

    @Input()
    timetableDate: Date;

    @Input()
    canModifyTimetable: boolean;

    lastPopoverRef: any;

    @HostListener('document:click', ['$event'])
    clickOutside(event) {
        // If there's a last element-reference AND the click-event target is outside this element
        if (this.lastPopoverRef && !this.lastPopoverRef._elementRef.nativeElement.contains(event.target)) {
            this.lastPopoverRef.close();
            this.lastPopoverRef = null;
        }
    }

    setCurrentPopoverOpen(popReference) {
        // If there's a last element-reference AND the new reference is different
        if (this.lastPopoverRef && this.lastPopoverRef !== popReference) {
            this.lastPopoverRef.close();
        }
        // Registering new popover ref
        this.lastPopoverRef = popReference;
    }

    goToEdit() {
        console.log('goToEdit')
    }

}
