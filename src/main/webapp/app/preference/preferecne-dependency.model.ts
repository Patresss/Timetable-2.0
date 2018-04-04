export class PreferenceDependency {
    constructor(
        public subjectId?: number,
        public teacherId?: number,
        public divisionId?: number,
        public placeId?: number,
        public periodId?: number,
        public lessonId?: number,
        public date?: number,
        public inMonday?: number,
        public inTuesday?: number,
        public inWednesday?: number,
        public inThursday?: number,
        public inFriday?: number,
        public inSaturday?: number,
        public inSunday?: number,
        public everyWeek?: number,
        public startWithWeek?: number,
        public startTimeString?: number,
        public endTimeString?: number) {
    }

}
