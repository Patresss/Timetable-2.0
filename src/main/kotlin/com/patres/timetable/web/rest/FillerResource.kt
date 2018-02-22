package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.domain.AbstractApplicationEntity
import com.patres.timetable.domain.Lesson
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.enumeration.EventType
import com.patres.timetable.service.DivisionService
import com.patres.timetable.service.LessonService
import com.patres.timetable.service.SubjectService
import com.patres.timetable.service.TimetableService
import com.patres.timetable.service.TeacherService
import com.patres.timetable.service.PeriodService
import com.patres.timetable.service.PlaceService
import com.patres.timetable.service.dto.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.net.URISyntaxException
import java.time.LocalDate

/**
 * REST controller for managing Lesson.
 */
@RestController
@RequestMapping("/api")
open class FillerResource(
    private val lessonService: LessonService,
    private var divisionService: DivisionService,
    private var subjectService: SubjectService,
    private var timetableService: TimetableService,
    private var periodService: PeriodService,
    private var teacherService: TeacherService,
    private var placeService: PlaceService) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(FillerResource::class.java)
    }

    @PostMapping(value = "/fill")
    @Timed
    @Throws(URISyntaxException::class)
    open fun fill() {
        FillerResource.log.debug("Fill database")

        // =====================================================
        // Division
        // =====================================================
        val lo2 = createDivision(name = "II Liceum Ogólnokształcące im. Konstytucji 3 Maja w Krośnie", shortName = "2 LO", divisionType = DivisionType.SCHOOL, numberOfPeople = 572)
        val class1a = createDivision(name = "1 A", shortName = "1 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class1b = createDivision(name = "1 B", shortName = "1 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class1c = createDivision(name = "1 C", shortName = "1 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class1d = createDivision(name = "1 D", shortName = "1 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class1e = createDivision(name = "1 E", shortName = "1 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class1f = createDivision(name = "1 F", shortName = "1 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class2a = createDivision(name = "2 A", shortName = "1 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class2b = createDivision(name = "2 B", shortName = "2 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class2c = createDivision(name = "2 C", shortName = "2 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class2d = createDivision(name = "2 D", shortName = "2 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class2e = createDivision(name = "2 E", shortName = "2 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class2f = createDivision(name = "2 F", shortName = "2 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class3a = createDivision(name = "3 A", shortName = "3 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class3b = createDivision(name = "3 B", shortName = "3 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class3c = createDivision(name = "3 C", shortName = "3 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class3d = createDivision(name = "3 D", shortName = "3 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class3e = createDivision(name = "3 E", shortName = "3 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val class3f = createDivision(name = "3 F", shortName = "3 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        val div1aG1 = createDivision(name = "1A gr	 1", shortName = "Ang 1A gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        val div1aG2 = createDivision(name = "1A gr	 2", shortName = "Ang 1A gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        val divNie1G1 = createDivision(name = "Niemiecki 1 gr	 1", shortName = "Niem 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        val divNie1G2 = createDivision(name = "Niemiecki 1 gr	 2", shortName = "Niem 1 gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b))
        val divFra1G1 = createDivision(name = "Francuski 1 gr	 1", shortName = "Fra 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        val divRos1G1 = createDivision(name = "Rosyjski 1 gr	 1", shortName = "Ros 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        val divRos1G2 = createDivision(name = "Rosyjski 1 gr	 2", shortName = "Ros 1 gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        val div1GrCh1 = createDivision(name = "WF 1 gr	 Chłopcy 1", shortName = "WF 1 gr	 Ch 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        val div1GrCh2 = createDivision(name = "WF 1 gr	 Chłopcy 2", shortName = "WF 1 gr	 Ch 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b, class1c))
        val div1GrDz1 = createDivision(name = "WF 1 gr	 Dziewczyny 1", shortName = "WF 1 gr	 Dz 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a, class1c))
        val div1GrDz2 = createDivision(name = "WF 1 gr	 Dziewczyny 2", shortName = "WF 1 gr	 Dz 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1c))
        val div1bG1 = createDivision(name = "1B gr	 1", shortName = "Ang 1B gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b))
        val div1bG2 = createDivision(name = "1B gr	 2", shortName = "Ang 1B gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b))
        val div1Gr1bDz1 = createDivision(name = "WF 1 gr	 Dziewczyny 1", shortName = "WF 1 gr	 Dz 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b))

        // =====================================================
        // Subject
        // =====================================================
        val historia = createSubject(name = "Historia", shortName = "HIST", divisionOwner = lo2)
        val wiedzaOSpołeczeństwie = createSubject(name = "Wiedza o Społeczeństwie", shortName = "WOS", divisionOwner = lo2)
        val wiedzaOKulturze = createSubject(name = "Wiedza o kulturze", shortName = "WOK", divisionOwner = lo2)
        val matematyka = createSubject(name = "Matematyka", shortName = "MAT", divisionOwner = lo2)
        val podstawyPrzedsiębiorczości = createSubject(name = "Podstawy przedsiębiorczości", shortName = "PP", divisionOwner = lo2)
        val informatyka = createSubject(name = "Informatyka", shortName = "INF", divisionOwner = lo2)
        val biologia = createSubject(name = "Biologia", shortName = "BIO", divisionOwner = lo2)
        val chemia = createSubject(name = "Chemia", shortName = "CHEM", divisionOwner = lo2)
        val geografia = createSubject(name = "Geografia", shortName = "GEO", divisionOwner = lo2)
        val fizyka = createSubject(name = "Fizyka", shortName = "FIZ", divisionOwner = lo2)
        val jPolski = createSubject(name = "J. polski", shortName = "J. POL", divisionOwner = lo2)
        val jAngielski = createSubject(name = "J. angielski", shortName = "ANG", divisionOwner = lo2)
        val jNiemiecki = createSubject(name = "J. niemiecki", shortName = "NMC", divisionOwner = lo2)
        val jFrancuski = createSubject(name = "J. francuski", shortName = "FR", divisionOwner = lo2)
        val jLaciński = createSubject(name = "J. łaciński", shortName = "ŁAC", divisionOwner = lo2)
        val jRosyjski = createSubject(name = "J. rosyjski", shortName = "ROS", divisionOwner = lo2)
        val godzWych = createSubject(name = "Godzina Wychowawcza", shortName = "GW", divisionOwner = lo2)
        val edukacjaDoBezpieczeństwa = createSubject(name = "Edukacja do bezpieczeństwa", shortName = "EDB", divisionOwner = lo2)
        val wychowanieFizyczne = createSubject(name = "Wychowanie Fizyczne", shortName = "WF", divisionOwner = lo2)
        val religia = createSubject(name = "Religia", shortName = "REL", divisionOwner = lo2)
        val wychowaniedoZyciaWRodzinie = createSubject(name = "Wychowanie do życia w rodzinie", shortName = "WDŻWR", divisionOwner = lo2)

        // =====================================================
        // Teacher
        // =====================================================
        val deptuch = createTeacher(degree = "mgr", name = "Witold", surname = "Deptuch", divisionOwner = lo2, preferredSubjects = setOf(biologia))
        val janusz = createTeacher(degree = "mgr", name = "Edyta", surname = "Janusz", divisionOwner = lo2, preferredSubjects = setOf(jFrancuski))
        val stasik = createTeacher(degree = "mgr", name = "Bogusława", surname = "Stasik", divisionOwner = lo2)
        val urbanek = createTeacher(degree = "mgr", name = "Jadwiga", surname = "Urbanek", divisionOwner = lo2)
        val gierlach = createTeacher(degree = "mgr", name = "Anna", surname = "Gierlach", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        val klein = createTeacher(degree = "mgr", name = "Lucyna", surname = "Klein", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        val pernal = createTeacher(degree = "mgr", name = "Renata", surname = "Pernal", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        val grodeckaZaremba = createTeacher(degree = "mgr", name = "Tamara", surname = "Grodecka-Zaremba", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        val prajsnar = createTeacher(degree = "mgr", name = "Arkadiusz", surname = "Prajsnar", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        val dynowski = createTeacher(degree = "mgr", name = "Lucjan", surname = "Dynowski", divisionOwner = lo2, preferredSubjects = setOf(historia, wiedzaOSpołeczeństwie))
        val longosz = createTeacher(degree = "dr", name = "Elżbieta", surname = "Longosz", divisionOwner = lo2, preferredSubjects = setOf(historia, wiedzaOSpołeczeństwie))
        val świstak = createTeacher(degree = "mgr", name = "Elżbieta", surname = "Świstak", divisionOwner = lo2, preferredSubjects = setOf(historia, wiedzaOSpołeczeństwie))
        val baran = createTeacher(degree = "mgr", name = "Elżbieta", surname = "Baran", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        val karnas = createTeacher(degree = "mgr", name = "Monika", surname = "Karnas", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        val kasprzyk = createTeacher(degree = "mgr", name = "Tomasz", surname = "Kasprzyk", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        val kijowska = createTeacher(degree = "mgr", name = "Beata", surname = "Kijowska", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        val kolanko = createTeacher(degree = "mgr", name = "Irena", surname = "Kolanko", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        val trybusGorczyca = createTeacher(degree = "mgr", name = "Agnieszka", surname = "Trybus-Gorczyca", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        val czuba = createTeacher(degree = "mgr", name = "Beata", surname = "Czuba", divisionOwner = lo2, preferredSubjects = setOf(matematyka))
        val jastrzębska = createTeacher(degree = "mgr", name = "Mariola", surname = "Jastrzębska", divisionOwner = lo2, preferredSubjects = setOf(matematyka))
        val mięsowicz = createTeacher(degree = "mgr", name = "Jolanta", surname = "Mięsowicz", divisionOwner = lo2, preferredSubjects = setOf(matematyka))
        val hadel = createTeacher(degree = "mgr", name = "Anna", surname = "Hadel", divisionOwner = lo2, preferredSubjects = setOf(jNiemiecki))
        val przybyłowicz = createTeacher(degree = "mgr", name = "Anna", surname = "Przybyłowicz", divisionOwner = lo2, preferredSubjects = setOf(jNiemiecki))
        val przybyłowiczCiszewska = createTeacher(degree = "mgr", name = "Katarzyna", surname = "Przybyłowicz-Ciszewska", divisionOwner = lo2, preferredSubjects = setOf(jNiemiecki))
        val rachwał = createTeacher(degree = "mgr", name = "Beata", surname = "Rachwał", divisionOwner = lo2, preferredSubjects = setOf(jFrancuski))
        val chodorowiczBąk = createTeacher(degree = "dr", name = "Agata", surname = "Chodorowicz-Bąk", divisionOwner = lo2, preferredSubjects = setOf(chemia))
        val serwatka = createTeacher(degree = "mgr", name = "Monika", surname = "Serwatka", divisionOwner = lo2, preferredSubjects = setOf(chemia))
        val gonet = createTeacher(degree = "mgr", name = "Tatiana", surname = "Gonet", divisionOwner = lo2, preferredSubjects = setOf(geografia))
        val guzik = createTeacher(degree = "mgr", name = "Maciej", surname = "Guzik", divisionOwner = lo2, preferredSubjects = setOf(biologia))
        val szarłowicz = createTeacher(degree = "mgr", name = "Tomasz", surname = "Szarłowicz", divisionOwner = lo2, preferredSubjects = setOf(biologia))
        val dawidkoJ = createTeacher(degree = "mgr", name = "Jacek", surname = "Dawidko", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        val wilk = createTeacher(degree = "mgr", name = "Wojciech", surname = "Wilk", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        val zając = createTeacher(degree = "mgr", name = "Tomasz", surname = "Zając", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        val dawidkoR = createTeacher(degree = "mgr", name = "Renata", surname = "Dawidko", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        val rachfał = createTeacher(degree = "mgr", name = "Maria", surname = "Rachfał", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        val bloch = createTeacher(degree = "mgr inż	", name = "Sławomir", surname = "Bloch", divisionOwner = lo2, preferredSubjects = setOf(fizyka))
        val józefczyk = createTeacher(degree = "mgr", name = "Stanisław", surname = "Józefczyk", divisionOwner = lo2, preferredSubjects = setOf(fizyka))
        val szott = createTeacher(degree = "mgr", name = "Irma", surname = "Szott", divisionOwner = lo2, preferredSubjects = setOf(fizyka))
        val dzierwa = createTeacher(degree = "mgr", name = "Małgorzata", surname = "Dzierwa", divisionOwner = lo2, preferredSubjects = setOf(informatyka))
        val solecki = createTeacher(degree = "mgr", name = "Ryszard", surname = "Solecki", divisionOwner = lo2, preferredSubjects = setOf(informatyka))
        val kudroń = createTeacher(degree = "mgr", name = "Katarzyna", surname = "Kudroń", divisionOwner = lo2, preferredSubjects = setOf(jLaciński))
        val pPiernal = createTeacher(degree = "ks	 mgr", name = "Pernal", surname = "Piotr", divisionOwner = lo2, preferredSubjects = setOf(religia))
        val tPiwinski = createTeacher(degree = "ks	 mgr", name = "Piwiński", surname = "Tadeusz", divisionOwner = lo2, preferredSubjects = setOf(religia))
        val matwiej = createTeacher(degree = "mgr", name = "Wojciech", surname = "Matwiej", divisionOwner = lo2)
        val twardzikWilk = createTeacher(degree = "mgr", name = "Małgorzata", surname = "Twardzik-Wilk", divisionOwner = lo2, preferredSubjects = setOf(wiedzaOKulturze))
        val suchodolski = createTeacher(degree = "mgr inż	", name = "Zbigniew", surname = "Suchodolski", divisionOwner = lo2, preferredSubjects = setOf(podstawyPrzedsiębiorczości))
        val mSuchodolski = createTeacher(degree = "mgr", name = "Mateusz", surname = "Suchodolski", divisionOwner = lo2, preferredSubjects = setOf(podstawyPrzedsiębiorczości))
        val łopuszańska = createTeacher(degree = "mgr", name = "Dorota ", surname = "Łopuszańska-Patrylak", divisionOwner = lo2, preferredSubjects = setOf(jRosyjski))

        // =====================================================
        // Lesson
        // =====================================================
        val l0 = createLesson(name = "0", startTime = "07:10", endTime = "08:45", divisionOwner = lo2)
        val l1 = createLesson(name = "1", startTime = "08:00", endTime = "08:45", divisionOwner = lo2)
        val l2 = createLesson(name = "2", startTime = "08:50", endTime = "09:35", divisionOwner = lo2)
        val l3 = createLesson(name = "3", startTime = "09:45", endTime = "10:30", divisionOwner = lo2)
        val l4 = createLesson(name = "4", startTime = "10:35", endTime = "11:20", divisionOwner = lo2)
        val l5 = createLesson(name = "5", startTime = "11:25", endTime = "12:10", divisionOwner = lo2)
        val l6 = createLesson(name = "6", startTime = "12:30", endTime = "13:15", divisionOwner = lo2)
        val l7 = createLesson(name = "7", startTime = "13:20", endTime = "14:05", divisionOwner = lo2)
        val l8 = createLesson(name = "8", startTime = "14:10", endTime = "14:55", divisionOwner = lo2)
        val l9 = createLesson(name = "9", startTime = "15:00", endTime = "15:45", divisionOwner = lo2)
        val l10 = createLesson(name = "10", startTime = "15:50", endTime = "16:35", divisionOwner = lo2)
        val l11 = createLesson(name = "11", startTime = "16:40", endTime = "17:25", divisionOwner = lo2)

        // =====================================================
        // Place
        // =====================================================
        val p4 = createPlace(name = "4", numberOfSeats = 34, preferredSubjects = setOf(informatyka), division = lo2)
        val p5 = createPlace(name = "5", numberOfSeats = 34, division = lo2)
        val p6 = createPlace(name = "6", numberOfSeats = 34, division = lo2)
        val p7 = createPlace(name = "7", numberOfSeats = 34, division = lo2)
        val p7g = createPlace(name = "7g", numberOfSeats = 16, division = lo2)
        val p8 = createPlace(name = "8", numberOfSeats = 34, preferredSubjects = setOf(biologia), division = lo2)
        val p10 = createPlace(name = "10", numberOfSeats = 34, division = lo2)
        val p11 = createPlace(name = "11", numberOfSeats = 34, division = lo2)
        val p12 = createPlace(name = "12", numberOfSeats = 34, division = lo2)
        val p13 = createPlace(name = "13", numberOfSeats = 34, division = lo2)
        val p14 = createPlace(name = "14", numberOfSeats = 34, division = lo2)
        val p15 = createPlace(name = "15", numberOfSeats = 34, preferredSubjects = setOf(fizyka), division = lo2)
        val p16 = createPlace(name = "16", numberOfSeats = 34, division = lo2)
        val p20 = createPlace(name = "20", numberOfSeats = 34, preferredSubjects = setOf(geografia), division = lo2)
        val p21 = createPlace(name = "21", numberOfSeats = 34, division = lo2)
        val p22 = createPlace(name = "22", numberOfSeats = 34, preferredSubjects = setOf(matematyka), division = lo2)
        val p24 = createPlace(name = "24", numberOfSeats = 34, preferredSubjects = setOf(chemia), division = lo2)
        val p25 = createPlace(name = "25", numberOfSeats = 34, division = lo2)
        val p31 = createPlace(name = "31", numberOfSeats = 34, preferredSubjects = setOf(edukacjaDoBezpieczeństwa), division = lo2)
        val p35 = createPlace(name = "35", numberOfSeats = 34, division = lo2)
        val p36 = createPlace(name = "36", numberOfSeats = 34, division = lo2)
        val pds = createPlace(name = "Duża Sala", shortName = "DS", numberOfSeats = 180, division = lo2)
        val pS = createPlace(name = "Siłownia", shortName = "S", numberOfSeats = 34, division = lo2)
        val pb = createPlace(name = "B", shortName = "B", numberOfSeats = 180, division = lo2)
        val ph = createPlace(name = "H", shortName = "H", numberOfSeats = 180, division = lo2)
        val pG4 = createPlace(name = "Gimnazjum 4", shortName = "G4", numberOfSeats = 32, division = lo2)


        // =====================================================
        // Period Interval
        // =====================================================
        val interval = IntervalDTO(startDate = LocalDate.parse("2016-09-01"), endDate = LocalDate.parse("2017-02-01"), included = true)
        var semestrZimowyPeriod = PeriodDTO(name = "Semestr zimowy 2016/2017", intervalTimes = setOf(interval), divisionOwnerId = lo2.id)
        semestrZimowyPeriod = periodService.save(semestrZimowyPeriod)

        // =====================================================
        // Timetable
        // =====================================================
        // Klasa 1a
        val d1aMon2 = createTimetable( inMonday = true, lesson = l2, subject = biologia, teacher = szarłowicz, place = p8, division = class1a, period = semestrZimowyPeriod)
        val d1aMon3 = createTimetable( inMonday = true, lesson = l3, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, place = p35, division = class1a, period = semestrZimowyPeriod)
        val d1aMon4a = createTimetable( inMonday = true, lesson = l4, subject = jAngielski, teacher = kijowska, place = p7g, division = div1aG1, period = semestrZimowyPeriod)
        val d1aMon4b = createTimetable( inMonday = true, lesson = l4, subject = jAngielski, teacher = kasprzyk, place = p21, division = div1aG2, period = semestrZimowyPeriod)
        val d1aMon5 = createTimetable( inMonday = true, lesson = l5, subject = religia, teacher = tPiwinski, place = p36, division = class1a, period = semestrZimowyPeriod)
        val d1aMon6 = createTimetable( inMonday = true, lesson = l6, subject = fizyka, teacher = szott, place = p15, division = class1a, period = semestrZimowyPeriod)
        val d1aMon8 = createTimetable( inMonday = true, lesson = l8, subject = historia, teacher = świstak, place = p7, division = class1a, period = semestrZimowyPeriod)
        val d1aMon9Test = createTimetable( inMonday = true, lesson = l9, subject = historia, teacher = świstak, place = p7, division = class1a, period = semestrZimowyPeriod, everyWeek = 2)

        val d1aTue1 = createTimetable( inTuesday = true, lesson = l1, subject = jPolski, teacher = pernal, place = p24, division = class1a, period = semestrZimowyPeriod)
        val d1aTue2 = createTimetable( inTuesday = true, lesson = l2, subject = geografia, teacher = gonet, place = p20, division = class1a, period = semestrZimowyPeriod)
        val d1aTue3a = createTimetable( inTuesday = true, lesson = l3, subject = jAngielski, teacher = kijowska, place = p4, division = div1aG1, period = semestrZimowyPeriod)
        val d1aTue3b = createTimetable( inTuesday = true, lesson = l3, subject = jAngielski, teacher = kasprzyk, place = p7g, division = div1aG2, period = semestrZimowyPeriod)
        val d1aTue4 = createTimetable( inTuesday = true, lesson = l4, subject = matematyka, teacher = czuba, place = p22, division = class1a, period = semestrZimowyPeriod)
        val d1aTue5 = createTimetable( inTuesday = true, lesson = l5, subject = matematyka, teacher = czuba, place = p22, division = class1a, period = semestrZimowyPeriod)
        val d1aTue6 = createTimetable( inTuesday = true, lesson = l6, subject = chemia, teacher = chodorowiczBąk, place = p24, division = class1a, period = semestrZimowyPeriod)
        val d1aTue7 = createTimetable( inTuesday = true, lesson = l7, subject = fizyka, teacher = szott, place = p15, division = class1a, period = semestrZimowyPeriod)

        val d1aWen1 = createTimetable( inWednesday= true, lesson = l1, subject = wiedzaOSpołeczeństwie, teacher = świstak, place = p24, division = class1a, period = semestrZimowyPeriod)
        val d1aWen3 = createTimetable( inWednesday= true, lesson = l3, subject = historia, teacher = świstak, place = p24, division = class1a, period = semestrZimowyPeriod)
        val d1aWen4 = createTimetable( inWednesday= true, lesson = l4, subject = wychowaniedoZyciaWRodzinie, teacher = solecki, place = p35, division = class1a, period = semestrZimowyPeriod)

        val d1aThu1 = createTimetable( inThursday= true, lesson = l1, subject = podstawyPrzedsiębiorczości, teacher = mSuchodolski, place = p36, division = class1a, period = semestrZimowyPeriod)
        val d1aThu2 = createTimetable( inThursday= true, lesson = l2, subject = matematyka, teacher = czuba, place = p22, division = class1a, period = semestrZimowyPeriod)
        val d1aThu3 = createTimetable( inThursday= true, lesson = l3, subject = wiedzaOKulturze, teacher = twardzikWilk, place = p13, division = class1a, period = semestrZimowyPeriod)
        val d1aThu4 = createTimetable( inThursday= true, lesson = l4, subject = edukacjaDoBezpieczeństwa, teacher = bloch, place = p31, division = class1a, period = semestrZimowyPeriod)
        val d1aThu5a = createTimetable( inThursday= true, lesson = l5, subject = jAngielski, teacher = kijowska, place = p14, division = div1aG1, period = semestrZimowyPeriod)
        val d1aThu5b = createTimetable( inThursday= true, lesson = l5, subject = informatyka, teacher = dzierwa, place = p4, division = div1aG2, period = semestrZimowyPeriod)
        val d1aThu6a = createTimetable( inThursday= true, lesson = l6, subject = informatyka, teacher = dzierwa, place = p4, division = div1aG1, period = semestrZimowyPeriod)
        val d1aThu6b = createTimetable( inThursday= true, lesson = l6, subject = jAngielski, teacher = kasprzyk, place = p5, division = div1aG2, period = semestrZimowyPeriod)

        val d1aThu7 = createTimetable( inThursday= true, lesson = l7, subject = godzWych, teacher = czuba, place = p22, division = class1a, period = semestrZimowyPeriod)

        val d1aFri4 = createTimetable( inFriday= true, lesson = l4, subject = jPolski, teacher = pernal, place = p16, division = class1a, period = semestrZimowyPeriod)
        val d1aFri5 = createTimetable( inFriday= true, lesson = l5, subject = jPolski, teacher = pernal, place = p16, division = class1a, period = semestrZimowyPeriod)
        val d1aFri6 = createTimetable( inFriday= true, lesson = l6, subject = matematyka, teacher = czuba, place = p36, division = class1a, period = semestrZimowyPeriod)
        val d1aFri8 = createTimetable( inFriday= true, lesson = l8, subject = religia, teacher = tPiwinski, place = p22, division = class1a, period = semestrZimowyPeriod)

        val d1aMon7a = createTimetable( inMonday = true, lesson = l7, subject = jNiemiecki, teacher = hadel, place = p14, division = divNie1G2, period = semestrZimowyPeriod)
        val d1aMon7b = createTimetable( inMonday = true, lesson = l7, subject = jFrancuski, teacher = rachwał, place = p25, division = divFra1G1, period = semestrZimowyPeriod)
        val d1aMon7c = createTimetable( inMonday = true, lesson = l7, subject = jRosyjski, teacher = łopuszańska, place = p6, division = divRos1G1, period = semestrZimowyPeriod)
        val d1aMon7d = createTimetable( inMonday = true, lesson = l7, subject = jRosyjski, teacher = stasik, place = p10, division = divRos1G2, period = semestrZimowyPeriod)

        val d1aWen2a = createTimetable( inWednesday= true, lesson = l2, subject = jNiemiecki, teacher = hadel, place = p14, division = divNie1G2, period = semestrZimowyPeriod)
        val d1vWen2b = createTimetable( inWednesday= true, lesson = l2, subject = jFrancuski, teacher = rachwał, place = p13, division = divFra1G1, period = semestrZimowyPeriod)
        val d1aWen2c = createTimetable( inWednesday= true, lesson = l2, subject = jRosyjski, teacher = łopuszańska, place = pG4, division = divRos1G1, period = semestrZimowyPeriod)
        val d1aWen2d = createTimetable( inWednesday= true, lesson = l2, subject = jRosyjski, teacher = stasik, place = p12, division = divRos1G2, period = semestrZimowyPeriod)

        val d1aFri7a = createTimetable( inFriday= true, lesson = l7, subject = jNiemiecki, teacher = hadel, place = p14, division = divNie1G2, period = semestrZimowyPeriod)
        val d1vFri7b = createTimetable( inFriday= true, lesson = l7, subject = jFrancuski, teacher = rachwał, place = p24, division = divFra1G1, period = semestrZimowyPeriod)
        val d1aFri7c = createTimetable( inFriday= true, lesson = l7, subject = jRosyjski, teacher = łopuszańska, place = p6, division = divRos1G1, period = semestrZimowyPeriod)
        val d1aFri7d = createTimetable( inFriday= true, lesson = l7, subject = jRosyjski, teacher = stasik, place = p13, division = divRos1G2, period = semestrZimowyPeriod)

        val d1WfGrDz1Fri2 = createTimetable( inFriday= true, lesson = l2, subject = wychowanieFizyczne, teacher = wilk, place = ph, division = div1GrDz1, period = semestrZimowyPeriod)
        val d1WfGrCh1Fri2 = createTimetable( inFriday= true, lesson = l2, subject = wychowanieFizyczne, teacher = zając, place = ph, division = div1GrCh1, period = semestrZimowyPeriod)
        val d1WfGrCh2Fri2 = createTimetable( inFriday= true, lesson = l2, subject = wychowanieFizyczne, teacher = dawidkoJ, place = ph, division = div1GrCh2, period = semestrZimowyPeriod)

        val d1WfGrDz1Fri3 = createTimetable( inFriday= true, lesson = l3, subject = wychowanieFizyczne, teacher = wilk, place = ph, division = div1GrDz1, period = semestrZimowyPeriod)
        val d1WfGrDz2Fri3 = createTimetable( inFriday= true, lesson = l3, subject = wychowanieFizyczne, teacher = rachfał, place = pS, division = div1GrDz2, period = semestrZimowyPeriod)
        val d1WfGrCh1Fri3 = createTimetable( inFriday= true, lesson = l3, subject = wychowanieFizyczne, teacher = zając, place = ph, division = div1GrCh1, period = semestrZimowyPeriod)
        val d1WfGrCh2Fri3 = createTimetable( inFriday= true, lesson = l3, subject = wychowanieFizyczne, teacher = dawidkoJ, place = ph, division = div1GrCh2, period = semestrZimowyPeriod)

        //Klasa 1B
        val d1bMon2 = createTimetable( inMonday = true, lesson = l2, subject = podstawyPrzedsiębiorczości, teacher = mSuchodolski, place = p10, division = class1b, period = semestrZimowyPeriod)
        val d1bMon3 = createTimetable( inMonday = true, lesson = l3, subject = jPolski, teacher = klein, place = p13, division = class1b, period = semestrZimowyPeriod)
        val d1bMon4 = createTimetable( inMonday = true, lesson = l4, subject = jPolski, teacher = klein, place = p13, division = class1b, period = semestrZimowyPeriod)
        val d1bMon5 = createTimetable( inMonday = true, lesson = l5, subject = edukacjaDoBezpieczeństwa, teacher = bloch, place = p31, division = class1b, period = semestrZimowyPeriod)
        val d1bMon6 = createTimetable( inMonday = true, lesson = l6, subject = historia, teacher = świstak, place = p35, division = class1b, period = semestrZimowyPeriod)
        val d1bMon7a = createTimetable( inMonday = true, lesson = l7, subject = jNiemiecki, teacher = przybyłowiczCiszewska, place = p13, division = divNie1G1, period = semestrZimowyPeriod)
        val d1bMon8 = createTimetable( inMonday = true, lesson = l8, subject = geografia, teacher = gonet, place = p20, division = class1b, period = semestrZimowyPeriod)

        val d1bTue5 = createTimetable( inTuesday = true, lesson = l5, subject = wiedzaOSpołeczeństwie, teacher = dynowski, place = p7, division = class1b, period = semestrZimowyPeriod)
        val d1aTue6a = createTimetable( inTuesday = true, lesson = l6, subject = jAngielski, teacher = karnas, place = p35, division = div1bG1, period = semestrZimowyPeriod)
        val d1aTue6b = createTimetable( inTuesday = true, lesson = l6, subject = jAngielski, teacher = kijowska, place = p6, division = div1bG2, period = semestrZimowyPeriod)
        val d1bTue7 = createTimetable( inTuesday = true, lesson = l7, subject = biologia, teacher = szarłowicz, place = p8, division = class1b, period = semestrZimowyPeriod)
        val d1bTue8 = createTimetable( inTuesday = true, lesson = l8, subject = chemia, teacher = chodorowiczBąk, place = p24, division = class1b, period = semestrZimowyPeriod)
        val d1bTue9a = createTimetable( inTuesday = true, lesson = l9, subject = wychowanieFizyczne, teacher = rachfał, place = pds, division = div1Gr1bDz1, period = semestrZimowyPeriod)
        val d1bTue10a = createTimetable( inTuesday = true, lesson = l10, subject = wychowanieFizyczne, teacher = rachfał, place = pds, division = div1Gr1bDz1, period = semestrZimowyPeriod)

        val d1bWen1 = createTimetable( inWednesday= true, lesson = l1, subject = matematyka, teacher = mięsowicz, place = p16, division = class1b, period = semestrZimowyPeriod)
        val d1bWen2a = createTimetable( inWednesday= true, lesson = l2, subject = jNiemiecki, teacher = przybyłowiczCiszewska, place = p25, division = divNie1G1, period = semestrZimowyPeriod)
        val d1bWen3a = createTimetable( inWednesday= true, lesson = l3, subject = jAngielski, teacher = karnas, place = p10, division = div1bG1, period = semestrZimowyPeriod)
        val d1bWen3b = createTimetable( inWednesday= true, lesson = l3, subject = informatyka, teacher = dzierwa, place = p4, division = div1bG2, period = semestrZimowyPeriod)
        val d1bWen4a = createTimetable( inWednesday= true, lesson = l4, subject = informatyka, teacher = dzierwa, place = p4, division = div1bG2, period = semestrZimowyPeriod)
        val d1bWen4b = createTimetable( inWednesday= true, lesson = l4, subject = jAngielski, teacher = kijowska, place = p14, division = div1bG2, period = semestrZimowyPeriod)
        val d1bWen5 = createTimetable( inWednesday= true, lesson = l5, subject = geografia, teacher = gonet, place = p20, division = class1b, period = semestrZimowyPeriod)
        val d1bWen6 = createTimetable( inWednesday= true, lesson = l6, subject = wiedzaOKulturze, teacher = twardzikWilk, place = p13, division = class1b, period = semestrZimowyPeriod)
        val d1bWen7 = createTimetable( inWednesday= true, lesson = l7, subject = godzWych, teacher = klein, place = p16, division = class1b, period = semestrZimowyPeriod)
        val d1bWen8 = createTimetable( inWednesday= true, lesson = l8, subject = religia, teacher = tPiwinski, place = p36, division = class1b, period = semestrZimowyPeriod)


        val d1bThu1 = createTimetable( inThursday= true, lesson = l1, subject = fizyka, teacher = józefczyk, place = p15, division = class1b, period = semestrZimowyPeriod)
        val d1bThu2 = createTimetable( inThursday= true, lesson = l2, subject = podstawyPrzedsiębiorczości, teacher = mSuchodolski, place = p14, division = class1b, period = semestrZimowyPeriod)
        val d1bThu3 = createTimetable( inThursday= true, lesson = l3, subject = matematyka, teacher = mięsowicz, place = p22, division = class1b, period = semestrZimowyPeriod)
        val d1bThu4 = createTimetable( inThursday= true, lesson = l4, subject = matematyka, teacher = mięsowicz, place = p22, division = class1b, period = semestrZimowyPeriod)
        val d1bThu5 = createTimetable( inThursday= true, lesson = l5, subject = religia, teacher = tPiwinski, place = p10, division = class1b, period = semestrZimowyPeriod)
        val d1bThu6 = createTimetable( inMonday = true, lesson = l6, subject = historia, teacher = świstak, place = p36, division = class1b, period = semestrZimowyPeriod)
        val d1bThu7 = createTimetable( inMonday = true, lesson = l7, subject = wychowaniedoZyciaWRodzinie, teacher = solecki, place = p36, division = class1b, period = semestrZimowyPeriod)


        val d1bFri4 = createTimetable( inFriday= true, lesson = l4, subject = matematyka, teacher = mięsowicz, place = p7, division = class1b, period = semestrZimowyPeriod)
        val d1bFri5a = createTimetable( inFriday= true, lesson = l5, subject = jAngielski, teacher = karnas, place = p11, division = div1bG1, period = semestrZimowyPeriod)
        val d1bFri5b = createTimetable( inFriday= true, lesson = l5, subject = jAngielski, teacher = kijowska, place = p13, division = div1bG2, period = semestrZimowyPeriod)
        val d1bFri6 = createTimetable( inFriday= true, lesson = l6, subject = jPolski, teacher = klein, place = p15, division = class1b, period = semestrZimowyPeriod)
        val d1bFri7a = createTimetable( inFriday= true, lesson = l7, subject = jNiemiecki, teacher = przybyłowiczCiszewska, place = p22, division = divNie1G1, period = semestrZimowyPeriod)
    }

    private fun createPlace(name: String, shortName: String? = null, numberOfSeats: Long?, preferredSubjects: Set<SubjectDTO> = emptySet(), division: DivisionDTO): PlaceDTO {
        val place = PlaceDTO(name = name, shortName = shortName, numberOfSeats = numberOfSeats, preferredSubjects = preferredSubjects, divisionOwnerId = division.id)
        return placeService.save(place)
    }

    private fun createSubject(name: String, shortName: String, divisionOwner: DivisionDTO): SubjectDTO {
        val subject = SubjectDTO(name = name, shortName = shortName, divisionOwnerId = divisionOwner.id)
        return subjectService.save(subject)
    }

    private fun createTeacher(degree: String, name: String, surname: String, shortName: String? = null, divisionOwner: DivisionDTO, preferredSubjects: Set<SubjectDTO> = emptySet()): TeacherDTO {
        val teacher = TeacherDTO(degree = degree, name = name, surname = surname, divisionOwnerId = divisionOwner.id, preferredSubjects = preferredSubjects)
        return teacherService.save(teacher)
    }

    private fun createLesson(name: String, startTime: String, endTime: String, divisionOwner: DivisionDTO): LessonDTO {
        val lesson = LessonDTO(name = name, divisionOwnerId = divisionOwner.id).also {
            it.startTime = AbstractApplicationEntity.getSecondsFromString(startTime)
            it.endTime = AbstractApplicationEntity.getSecondsFromString(endTime)
        }
        return lessonService.save(lesson)
    }

    private fun createDivision(name: String, shortName: String, divisionType: DivisionType, numberOfPeople: Long, parents: Set<DivisionDTO> = emptySet()): DivisionDTO {
        val division = DivisionDTO(name = name, shortName = shortName, divisionType = divisionType, numberOfPeople = numberOfPeople, parents = parents)
        return divisionService.save(division)
    }

    private fun createTimetable(
        subject: SubjectDTO,
        lesson: LessonDTO,
        teacher: TeacherDTO,
        place: PlaceDTO,
        division: DivisionDTO,
        period: PeriodDTO,
        type: EventType = EventType.LESSON,
        inMonday: Boolean = false,
        inTuesday: Boolean = false,
        inWednesday: Boolean = false,
        inThursday: Boolean = false,
        inFriday: Boolean = false,
        inSaturday: Boolean = false,
        inSunday: Boolean = false,
        everyWeek: Long = 1
    ): TimetableDTO {
        val timetable = TimetableDTO(
            title = subject.name,
            subjectId = subject.id,
            lessonId = lesson.id,
            teacherId = teacher.id,
            placeId = place.id,
            divisionId = division.id,
            periodId = period.id,
            type = type,
            inMonday = inMonday,
            inTuesday = inTuesday,
            inWednesday = inWednesday,
            inThursday = inThursday,
            inFriday = inFriday,
            inSaturday = inSaturday,
            inSunday = inSunday,
            everyWeek = everyWeek
        )
        return timetableService.save(timetable)
    }
}
