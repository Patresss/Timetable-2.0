export class PreferenceDependency {
    constructor(
        public subjectId?: number,
        public teacherId?: number,
        public divisionId?: number,
        public placeId?: number,
        public periodId?: number,
        public lessonId?: number,
        public divisionOwnerId?: number,
        public notTimetableId?: number,
        public date?: number,
        public inMonday?: boolean,
        public inTuesday?: boolean,
        public inWednesday?: boolean,
        public inThursday?: boolean,
        public inFriday?: boolean,
        public inSaturday?: boolean,
        public inSunday?: boolean,
        public everyWeek?: number,
        public startWithWeek?: number,
        public startTimeString?: string,
        public endTimeString?: string) {
    }

}
