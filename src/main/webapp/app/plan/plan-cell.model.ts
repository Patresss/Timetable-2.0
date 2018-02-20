import {Time} from './time.model';
import {Timetable} from '../entities/timetable';

export class PlanCell {

    startHeightPercent: number;
    endHeightPercent: number;

    constructor(public timetable: Timetable,
                private startHour: Time,
                private endHour: Time) {
        this.startHeightPercent = this.getPercent(timetable.startTime);
        this.endHeightPercent = 100.0 - this.getPercent(timetable.endTime);
    }

    private getPercent(time: Time) {
        return 100.0 * (time.getMinutes() - this.startHour.getMinutes()) / (this.endHour.getMinutes() - this.startHour.getMinutes());
    }

}
