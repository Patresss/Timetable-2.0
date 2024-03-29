export class DateObject {
    static fromDate(date: Date) {
        return new DateObject(date.getFullYear(), date.getMonth() + 1, date.getDate())
    }

    constructor(
        public year?: number,
        public month?: number,
        public day?: number
    ) {
    }

}
