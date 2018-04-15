package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.enumeration.EventType
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.service.*
import com.patres.timetable.service.dto.*
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForTeacherDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
    private var curriculumService: CurriculumService,
    private var placeService: PlaceService) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(FillerResource::class.java)
    }

    var lo2 = DivisionDTO()
    var class1a = DivisionDTO()
    var class1aGenerate = DivisionDTO()
    var class1b = DivisionDTO()
    var class1c = DivisionDTO()
    var class1d = DivisionDTO()
    var class1e = DivisionDTO()
    var class1f = DivisionDTO()
    var class2a = DivisionDTO()
    var class2b = DivisionDTO()
    var class2c = DivisionDTO()
    var class2d = DivisionDTO()
    var class2e = DivisionDTO()
    var class2f = DivisionDTO()
    var class3a = DivisionDTO()
    var class3b = DivisionDTO()
    var class3c = DivisionDTO()
    var class3d = DivisionDTO()
    var class3e = DivisionDTO()
    var class3f = DivisionDTO()
    var div1aG1 = DivisionDTO()
    var div1aG2 = DivisionDTO()
    var divNie1G1 = DivisionDTO()
    var divNie1G2 = DivisionDTO()
    var divFra1G1 = DivisionDTO()
    var divRos1G1 = DivisionDTO()
    var divRos1G2 = DivisionDTO()
    var div1GrCh1 = DivisionDTO()
    var div1GrCh2 = DivisionDTO()
    var div1GrDz1 = DivisionDTO()
    var div1GrDz2 = DivisionDTO()
    var div1bG1 = DivisionDTO()
    var div1bG2 = DivisionDTO()
    var div1Gr1bDz1 = DivisionDTO()

    var historia = SubjectDTO()
    var wiedzaOSpołeczeństwie = SubjectDTO()
    var wiedzaOKulturze = SubjectDTO()
    var matematyka = SubjectDTO()
    var podstawyPrzedsiębiorczości = SubjectDTO()
    var informatyka = SubjectDTO()
    var biologia = SubjectDTO()
    var chemia = SubjectDTO()
    var geografia = SubjectDTO()
    var fizyka = SubjectDTO()
    var jPolski = SubjectDTO()
    var jAngielski = SubjectDTO()
    var jNiemiecki = SubjectDTO()
    var jFrancuski = SubjectDTO()
    var jLaciński = SubjectDTO()
    var jRosyjski = SubjectDTO()
    var godzWych = SubjectDTO()
    var edukacjaDoBezpieczeństwa = SubjectDTO()
    var wychowanieFizyczne = SubjectDTO()
    var religia = SubjectDTO()
    var wychowaniedoZyciaWRodzinie = SubjectDTO()


    // =====================================================
    // Teacher
    // =====================================================
    var deptuch = TeacherDTO()
    var janusz = TeacherDTO()
    var stasik = TeacherDTO()
    var urbanek = TeacherDTO()
    var gierlach = TeacherDTO()
    var klein = TeacherDTO()
    var pernal = TeacherDTO()
    var grodeckaZaremba = TeacherDTO()
    var prajsnar = TeacherDTO()
    var dynowski = TeacherDTO()
    var longosz = TeacherDTO()
    var świstak = TeacherDTO()
    var baran = TeacherDTO()
    var karnas = TeacherDTO()
    var kasprzyk = TeacherDTO()
    var kijowska = TeacherDTO()
    var kolanko = TeacherDTO()
    var trybusGorczyca = TeacherDTO()
    var czuba = TeacherDTO()
    var jastrzębska = TeacherDTO()
    var mięsowicz = TeacherDTO()
    var hadel = TeacherDTO()
    var przybyłowicz = TeacherDTO()
    var przybyłowiczCiszewska = TeacherDTO()
    var rachwał = TeacherDTO()
    var chodorowiczBąk = TeacherDTO()
    var serwatka = TeacherDTO()
    var gonet = TeacherDTO()
    var guzik = TeacherDTO()
    var szarłowicz = TeacherDTO()
    var dawidkoJ = TeacherDTO()
    var wilk = TeacherDTO()
    var zając = TeacherDTO()
    var dawidkoR = TeacherDTO()
    var rachfał = TeacherDTO()
    var bloch = TeacherDTO()
    var józefczyk = TeacherDTO()
    var szott = TeacherDTO()
    var dzierwa = TeacherDTO()
    var solecki = TeacherDTO()
    var kudroń = TeacherDTO()
    var pPiernal = TeacherDTO()
    var tPiwinski = TeacherDTO()
    var matwiej = TeacherDTO()
    var twardzikWilk = TeacherDTO()
    var suchodolski = TeacherDTO()
    var mSuchodolski = TeacherDTO()
    var łopuszańska = TeacherDTO()

    // =====================================================
    // Lesson
    // =====================================================
    var l0 = LessonDTO()
    var l1 = LessonDTO()
    var l2 = LessonDTO()
    var l3 = LessonDTO()
    var l4 = LessonDTO()
    var l5 = LessonDTO()
    var l6 = LessonDTO()
    var l7 = LessonDTO()
    var l8 = LessonDTO()
    var l9 = LessonDTO()
    var l10 = LessonDTO()
    var l11 = LessonDTO()

    // =====================================================
    // Place
    // =====================================================
    var p4 = PlaceDTO()
    var p5 = PlaceDTO()
    var p6 = PlaceDTO()
    var p7 = PlaceDTO()
    var p7g = PlaceDTO()
    var p8 = PlaceDTO()
    var p10 = PlaceDTO()
    var p11 = PlaceDTO()
    var p12 = PlaceDTO()
    var p13 = PlaceDTO()
    var p14 = PlaceDTO()
    var p15 = PlaceDTO()
    var p16 = PlaceDTO()
    var p20 = PlaceDTO()
    var p21 = PlaceDTO()
    var p22 = PlaceDTO()
    var p24 = PlaceDTO()
    var p25 = PlaceDTO()
    var p31 = PlaceDTO()
    var p35 = PlaceDTO()
    var p36 = PlaceDTO()
    var pds = PlaceDTO()
    var pS = PlaceDTO()
    var pb = PlaceDTO()
    var ph = PlaceDTO()
    var pG4 = PlaceDTO()


    // =====================================================
    // Period Interval
    // =====================================================
    var interval = IntervalDTO()
    var semestLetniPeriod = PeriodDTO()

    // =====================================================
    // Timetable
    // =====================================================
    // Klasa 1a
    var d1aMon2 = TimetableDTO()
    var d1aMon3 = TimetableDTO()
    var d1aMon4a = TimetableDTO()
    var d1aMon4b = TimetableDTO()
    var d1aMon5 = TimetableDTO()
    var d1aMon6 = TimetableDTO()
    var d1aMon8 = TimetableDTO()
    var d1aMon9Test = TimetableDTO()

    var d1aTue1 = TimetableDTO()
    var d1aTue2 = TimetableDTO()
    var d1aTue3a = TimetableDTO()
    var d1aTue3b = TimetableDTO()
    var d1aTue4 = TimetableDTO()
    var d1aTue5 = TimetableDTO()
    var d1aTue6 = TimetableDTO()
    var d1aTue7 = TimetableDTO()

    var d1aWen1 = TimetableDTO()
    var d1aWen3 = TimetableDTO()
    var d1aWen4 = TimetableDTO()

    var d1aThu1 = TimetableDTO()
    var d1aThu2 = TimetableDTO()
    var d1aThu3 = TimetableDTO()
    var d1aThu4 = TimetableDTO()
    var d1aThu5a = TimetableDTO()
    var d1aThu5b = TimetableDTO()
    var d1aThu6a = TimetableDTO()
    var d1aThu6b = TimetableDTO()

    var d1aThu7 = TimetableDTO()

    var d1aFri4 = TimetableDTO()
    var d1aFri5 = TimetableDTO()
    var d1aFri6 = TimetableDTO()
    var d1aFri8 = TimetableDTO()

    var d1aMon7a = TimetableDTO()
    var d1aMon7b = TimetableDTO()
    var d1aMon7c = TimetableDTO()
    var d1aMon7d = TimetableDTO()

    var d1aWen2a = TimetableDTO()
    var d1vWen2b = TimetableDTO()
    var d1aWen2c = TimetableDTO()
    var d1aWen2d = TimetableDTO()

    var d1aFri7a = TimetableDTO()
    var d1vFri7b = TimetableDTO()
    var d1aFri7c = TimetableDTO()
    var d1aFri7d = TimetableDTO()

    var d1WfGrDz1Fri2 = TimetableDTO()
    var d1WfGrCh1Fri2 = TimetableDTO()
    var d1WfGrCh2Fri2 = TimetableDTO()

    var d1WfGrDz1Fri3 = TimetableDTO()
    var d1WfGrDz2Fri3 = TimetableDTO()
    var d1WfGrCh1Fri3 = TimetableDTO()
    var d1WfGrCh2Fri3 = TimetableDTO()

    //Klasa 1B	 = TimetableDTO(
    var d1bMon2 = TimetableDTO()
    var d1bMon3 = TimetableDTO()
    var d1bMon4 = TimetableDTO()
    var d1bMon5 = TimetableDTO()
    var d1bMon6 = TimetableDTO()
    var d1bMon7a = TimetableDTO()
    var d1bMon8 = TimetableDTO()

    var d1bTue5 = TimetableDTO()
    var d1aTue6a = TimetableDTO()
    var d1aTue6b = TimetableDTO()
    var d1bTue7 = TimetableDTO()
    var d1bTue8 = TimetableDTO()
    var d1bTue9a = TimetableDTO()
    var d1bTue10a = TimetableDTO()

    var d1bWen1 = TimetableDTO()
    var d1bWen2a = TimetableDTO()
    var d1bWen3a = TimetableDTO()
    var d1bWen3b = TimetableDTO()
    var d1bWen4a = TimetableDTO()
    var d1bWen4b = TimetableDTO()
    var d1bWen5 = TimetableDTO()
    var d1bWen6 = TimetableDTO()
    var d1bWen7 = TimetableDTO()
    var d1bWen8 = TimetableDTO()

    var d1bThu1 = TimetableDTO()
    var d1bThu2 = TimetableDTO()
    var d1bThu3 = TimetableDTO()
    var d1bThu4 = TimetableDTO()
    var d1bThu5 = TimetableDTO()
    var d1bThu6 = TimetableDTO()
    var d1bThu7 = TimetableDTO()

    var d1bFri4 = TimetableDTO()
    var d1bFri5a = TimetableDTO()
    var d1bFri5b = TimetableDTO()
    var d1bFri6 = TimetableDTO()
    var d1bFri7a = TimetableDTO()


    @PostMapping(value = ["/fill"])
    @Timed
    @Transactional
    @Throws(URISyntaxException::class)
    open fun fill() {
        FillerResource.log.debug("Fill database")
        // =====================================================
        // Division Owner
        // =====================================================
        lo2 = createDivision(name = "II Liceum Ogólnokształcące im. Konstytucji 3 Maja w Krośnie", shortName = "2 LO", divisionType = DivisionType.SCHOOL, numberOfPeople = 572)


        // =====================================================
        // Lesson
        // =====================================================
        l0 = createLesson(name = "0", startTime = "07:10", endTime = "08:45", divisionOwner = lo2)
        l1 = createLesson(name = "1", startTime = "08:00", endTime = "08:45", divisionOwner = lo2)
        l2 = createLesson(name = "2", startTime = "08:50", endTime = "09:35", divisionOwner = lo2)
        l3 = createLesson(name = "3", startTime = "09:45", endTime = "10:30", divisionOwner = lo2)
        l4 = createLesson(name = "4", startTime = "10:35", endTime = "11:20", divisionOwner = lo2)
        l5 = createLesson(name = "5", startTime = "11:25", endTime = "12:10", divisionOwner = lo2)
        l6 = createLesson(name = "6", startTime = "12:30", endTime = "13:15", divisionOwner = lo2)
        l7 = createLesson(name = "7", startTime = "13:20", endTime = "14:05", divisionOwner = lo2)
        l8 = createLesson(name = "8", startTime = "14:10", endTime = "14:55", divisionOwner = lo2)
        l9 = createLesson(name = "9", startTime = "15:00", endTime = "15:45", divisionOwner = lo2)
        l10 = createLesson(name = "10", startTime = "15:50", endTime = "16:35", divisionOwner = lo2)
        l11 = createLesson(name = "11", startTime = "16:40", endTime = "17:25", divisionOwner = lo2)


        // =====================================================
        // Subject
        // =====================================================
        historia = createSubject(name = "Historia", shortName = "HIST", divisionOwner = lo2)
        wiedzaOSpołeczeństwie = createSubject(name = "Wiedza o Społeczeństwie", shortName = "WOS", divisionOwner = lo2)
        wiedzaOKulturze = createSubject(name = "Wiedza o kulturze", shortName = "WOK", divisionOwner = lo2)
        matematyka = createSubject(name = "Matematyka", shortName = "MAT", divisionOwner = lo2)
        podstawyPrzedsiębiorczości = createSubject(name = "Podstawy przedsiębiorczości", shortName = "PP", divisionOwner = lo2)
        informatyka = createSubject(name = "Informatyka", shortName = "INF", divisionOwner = lo2)
        biologia = createSubject(name = "Biologia", shortName = "BIO", divisionOwner = lo2)
        chemia = createSubject(name = "Chemia", shortName = "CHEM", divisionOwner = lo2)
        geografia = createSubject(name = "Geografia", shortName = "GEO", divisionOwner = lo2)
        fizyka = createSubject(name = "Fizyka", shortName = "FIZ", divisionOwner = lo2)
        jPolski = createSubject(name = "J. polski", shortName = "J. POL", divisionOwner = lo2)
        jAngielski = createSubject(name = "J. angielski", shortName = "ANG", divisionOwner = lo2)
        jNiemiecki = createSubject(name = "J. niemiecki", shortName = "NMC", divisionOwner = lo2)
        jFrancuski = createSubject(name = "J. francuski", shortName = "FR", divisionOwner = lo2)
        jLaciński = createSubject(name = "J. łaciński", shortName = "ŁAC", divisionOwner = lo2)
        jRosyjski = createSubject(name = "J. rosyjski", shortName = "ROS", divisionOwner = lo2)
        godzWych = createSubject(name = "Godzina Wychowawcza", shortName = "GW", divisionOwner = lo2)
        edukacjaDoBezpieczeństwa = createSubject(name = "Edukacja do bezpieczeństwa", shortName = "EDB", divisionOwner = lo2)
        wychowanieFizyczne = createSubject(name = "Wychowanie Fizyczne", shortName = "WF", divisionOwner = lo2)
        religia = createSubject(name = "Religia", shortName = "REL", divisionOwner = lo2)
        wychowaniedoZyciaWRodzinie = createSubject(name = "Wychowanie do życia w rodzinie", shortName = "WDŻWR", divisionOwner = lo2)

        // =====================================================
        // Teacher
        // =====================================================
        deptuch = createTeacher(degree = "mgr", name = "Witold", surname = "Deptuch", divisionOwner = lo2, preferredSubjects = setOf(biologia))
        janusz = createTeacher(degree = "mgr", name = "Edyta", surname = "Janusz", divisionOwner = lo2, preferredSubjects = setOf(jFrancuski))
        stasik = createTeacher(degree = "mgr", name = "Bogusława", surname = "Stasik", divisionOwner = lo2)
        urbanek = createTeacher(degree = "mgr", name = "Jadwiga", surname = "Urbanek", divisionOwner = lo2)
        gierlach = createTeacher(degree = "mgr", name = "Anna", surname = "Gierlach", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        klein = createTeacher(degree = "mgr", name = "Lucyna", surname = "Klein", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        pernal = createTeacher(degree = "mgr", name = "Renata", surname = "Pernal", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        grodeckaZaremba = createTeacher(degree = "mgr", name = "Tamara", surname = "Grodecka-Zaremba", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        prajsnar = createTeacher(degree = "mgr", name = "Arkadiusz", surname = "Prajsnar", divisionOwner = lo2, preferredSubjects = setOf(jPolski))
        dynowski = createTeacher(degree = "mgr", name = "Lucjan", surname = "Dynowski", divisionOwner = lo2, preferredSubjects = setOf(historia, wiedzaOSpołeczeństwie))
        longosz = createTeacher(degree = "dr", name = "Elżbieta", surname = "Longosz", divisionOwner = lo2, preferredSubjects = setOf(historia, wiedzaOSpołeczeństwie))
        świstak = createTeacher(degree = "mgr", name = "Elżbieta", surname = "Świstak", divisionOwner = lo2, preferredSubjects = setOf(historia, wiedzaOSpołeczeństwie))
        baran = createTeacher(degree = "mgr", name = "Elżbieta", surname = "Baran", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        karnas = createTeacher(degree = "mgr", name = "Monika", surname = "Karnas", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        kasprzyk = createTeacher(degree = "mgr", name = "Tomasz", surname = "Kasprzyk", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        kijowska = createTeacher(degree = "mgr", name = "Beata", surname = "Kijowska", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        kolanko = createTeacher(degree = "mgr", name = "Irena", surname = "Kolanko", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        trybusGorczyca = createTeacher(degree = "mgr", name = "Agnieszka", surname = "Trybus-Gorczyca", divisionOwner = lo2, preferredSubjects = setOf(jAngielski))
        val preferenceDataTimeForTeacherCzuba = setOf(
            PreferenceDataTimeForTeacherDTO(lessonId = l1.id, dayOfWeek = 1, points = 10),
            PreferenceDataTimeForTeacherDTO(lessonId = l1.id, dayOfWeek = 5, points = -10000),
            PreferenceDataTimeForTeacherDTO(lessonId = l2.id, dayOfWeek = 1, points = 5)
        )
        czuba = createTeacher(degree = "mgr", name = "Beata", surname = "Czuba", divisionOwner = lo2, preferredSubjects = setOf(matematyka), preferenceDataTimeForTeachers = preferenceDataTimeForTeacherCzuba)
        jastrzębska = createTeacher(degree = "mgr", name = "Mariola", surname = "Jastrzębska", divisionOwner = lo2, preferredSubjects = setOf(matematyka))
        mięsowicz = createTeacher(degree = "mgr", name = "Jolanta", surname = "Mięsowicz", divisionOwner = lo2, preferredSubjects = setOf(matematyka))
        hadel = createTeacher(degree = "mgr", name = "Anna", surname = "Hadel", divisionOwner = lo2, preferredSubjects = setOf(jNiemiecki))
        przybyłowicz = createTeacher(degree = "mgr", name = "Anna", surname = "Przybyłowicz", divisionOwner = lo2, preferredSubjects = setOf(jNiemiecki))
        przybyłowiczCiszewska = createTeacher(degree = "mgr", name = "Katarzyna", surname = "Przybyłowicz-Ciszewska", divisionOwner = lo2, preferredSubjects = setOf(jNiemiecki))
        rachwał = createTeacher(degree = "mgr", name = "Beata", surname = "Rachwał", divisionOwner = lo2, preferredSubjects = setOf(jFrancuski))
        chodorowiczBąk = createTeacher(degree = "dr", name = "Agata", surname = "Chodorowicz-Bąk", divisionOwner = lo2, preferredSubjects = setOf(chemia))
        serwatka = createTeacher(degree = "mgr", name = "Monika", surname = "Serwatka", divisionOwner = lo2, preferredSubjects = setOf(chemia))
        gonet = createTeacher(degree = "mgr", name = "Tatiana", surname = "Gonet", divisionOwner = lo2, preferredSubjects = setOf(geografia))
        guzik = createTeacher(degree = "mgr", name = "Maciej", surname = "Guzik", divisionOwner = lo2, preferredSubjects = setOf(biologia))
        szarłowicz = createTeacher(degree = "mgr", name = "Tomasz", surname = "Szarłowicz", divisionOwner = lo2, preferredSubjects = setOf(biologia))
        dawidkoJ = createTeacher(degree = "mgr", name = "Jacek", surname = "Dawidko", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        wilk = createTeacher(degree = "mgr", name = "Wojciech", surname = "Wilk", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        zając = createTeacher(degree = "mgr", name = "Tomasz", surname = "Zając", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        dawidkoR = createTeacher(degree = "mgr", name = "Renata", surname = "Dawidko", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        rachfał = createTeacher(degree = "mgr", name = "Maria", surname = "Rachfał", divisionOwner = lo2, preferredSubjects = setOf(wychowanieFizyczne))
        bloch = createTeacher(degree = "mgr inż	", name = "Sławomir", surname = "Bloch", divisionOwner = lo2, preferredSubjects = setOf(fizyka))
        józefczyk = createTeacher(degree = "mgr", name = "Stanisław", surname = "Józefczyk", divisionOwner = lo2, preferredSubjects = setOf(fizyka))
        szott = createTeacher(degree = "mgr", name = "Irma", surname = "Szott", divisionOwner = lo2, preferredSubjects = setOf(fizyka))
        dzierwa = createTeacher(degree = "mgr", name = "Małgorzata", surname = "Dzierwa", divisionOwner = lo2, preferredSubjects = setOf(informatyka))
        solecki = createTeacher(degree = "mgr", name = "Ryszard", surname = "Solecki", divisionOwner = lo2, preferredSubjects = setOf(informatyka))
        kudroń = createTeacher(degree = "mgr", name = "Katarzyna", surname = "Kudroń", divisionOwner = lo2, preferredSubjects = setOf(jLaciński))
        pPiernal = createTeacher(degree = "ks	 mgr", name = "Pernal", surname = "Piotr", divisionOwner = lo2, preferredSubjects = setOf(religia))
        tPiwinski = createTeacher(degree = "ks	 mgr", name = "Piwiński", surname = "Tadeusz", divisionOwner = lo2, preferredSubjects = setOf(religia))
        matwiej = createTeacher(degree = "mgr", name = "Wojciech", surname = "Matwiej", divisionOwner = lo2)
        twardzikWilk = createTeacher(degree = "mgr", name = "Małgorzata", surname = "Twardzik-Wilk", divisionOwner = lo2, preferredSubjects = setOf(wiedzaOKulturze))
        suchodolski = createTeacher(degree = "mgr inż	", name = "Zbigniew", surname = "Suchodolski", divisionOwner = lo2, preferredSubjects = setOf(podstawyPrzedsiębiorczości))
        mSuchodolski = createTeacher(degree = "mgr", name = "Mateusz", surname = "Suchodolski", divisionOwner = lo2, preferredSubjects = setOf(podstawyPrzedsiębiorczości))
        łopuszańska = createTeacher(degree = "mgr", name = "Dorota ", surname = "Łopuszańska-Patrylak", divisionOwner = lo2, preferredSubjects = setOf(jRosyjski))

        // =====================================================
        // Division
        // =====================================================
        val preferredSubjects = setOf(historia, wiedzaOSpołeczeństwie, wiedzaOKulturze, matematyka, podstawyPrzedsiębiorczości, informatyka, biologia, chemia, geografia, fizyka, jPolski, jAngielski, jNiemiecki, jFrancuski, jRosyjski, godzWych, edukacjaDoBezpieczeństwa, wychowanieFizyczne, religia, wychowaniedoZyciaWRodzinie )
        class1a = createDivision(name = "1 A", shortName = "1 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2), preferredTeachers = setOf(czuba), preferredSubjects = preferredSubjects)
        class1aGenerate = createDivision(name = "1 A generate", shortName = "1 A G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class1b = createDivision(name = "1 B", shortName = "1 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class1c = createDivision(name = "1 C", shortName = "1 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class1d = createDivision(name = "1 D", shortName = "1 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class1e = createDivision(name = "1 E", shortName = "1 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class1f = createDivision(name = "1 F", shortName = "1 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class2a = createDivision(name = "2 A", shortName = "1 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class2b = createDivision(name = "2 B", shortName = "2 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class2c = createDivision(name = "2 C", shortName = "2 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class2d = createDivision(name = "2 D", shortName = "2 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class2e = createDivision(name = "2 E", shortName = "2 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class2f = createDivision(name = "2 F", shortName = "2 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class3a = createDivision(name = "3 A", shortName = "3 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class3b = createDivision(name = "3 B", shortName = "3 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class3c = createDivision(name = "3 C", shortName = "3 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class3d = createDivision(name = "3 D", shortName = "3 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class3e = createDivision(name = "3 E", shortName = "3 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        class3f = createDivision(name = "3 F", shortName = "3 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = setOf(lo2))
        div1aG1 = createDivision(name = "1A gr	 1", shortName = "Ang 1A gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        div1aG2 = createDivision(name = "1A gr	 2", shortName = "Ang 1A gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        divNie1G1 = createDivision(name = "Niemiecki 1 gr	 1", shortName = "Niem 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        divNie1G2 = createDivision(name = "Niemiecki 1 gr	 2", shortName = "Niem 1 gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b))
        divFra1G1 = createDivision(name = "Francuski 1 gr	 1", shortName = "Fra 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        divRos1G1 = createDivision(name = "Rosyjski 1 gr	 1", shortName = "Ros 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        divRos1G2 = createDivision(name = "Rosyjski 1 gr	 2", shortName = "Ros 1 gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        div1GrCh1 = createDivision(name = "WF 1 gr	 Chłopcy 1", shortName = "WF 1 gr	 Ch 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a))
        div1GrCh2 = createDivision(name = "WF 1 gr	 Chłopcy 2", shortName = "WF 1 gr	 Ch 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b, class1c))
        div1GrDz1 = createDivision(name = "WF 1 gr	 Dziewczyny 1", shortName = "WF 1 gr	 Dz 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1a, class1c))
        div1GrDz2 = createDivision(name = "WF 1 gr	 Dziewczyny 2", shortName = "WF 1 gr	 Dz 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1c))
        div1bG1 = createDivision(name = "1B gr	 1", shortName = "Ang 1B gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b))
        div1bG2 = createDivision(name = "1B gr	 2", shortName = "Ang 1B gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b))
        div1Gr1bDz1 = createDivision(name = "WF 1 gr	 Dziewczyny 1", shortName = "WF 1 gr	 Dz 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = setOf(class1b))


        // =====================================================
        // Place
        // =====================================================
        p4 = createPlace(name = "4", numberOfSeats = 34, preferredSubjects = setOf(informatyka), division = lo2)
        p5 = createPlace(name = "5", numberOfSeats = 34, division = lo2)
        p6 = createPlace(name = "6", numberOfSeats = 34, division = lo2)
        p7 = createPlace(name = "7", numberOfSeats = 34, division = lo2)
        p7g = createPlace(name = "7g", numberOfSeats = 16, division = lo2)
        p8 = createPlace(name = "8", numberOfSeats = 34, preferredSubjects = setOf(biologia), division = lo2)
        p10 = createPlace(name = "10", numberOfSeats = 34, division = lo2)
        p11 = createPlace(name = "11", numberOfSeats = 34, division = lo2)
        p12 = createPlace(name = "12", numberOfSeats = 34, division = lo2)
        p13 = createPlace(name = "13", numberOfSeats = 34, division = lo2)
        p14 = createPlace(name = "14", numberOfSeats = 34, division = lo2)
        p15 = createPlace(name = "15", numberOfSeats = 34, preferredSubjects = setOf(fizyka), division = lo2)
        p16 = createPlace(name = "16", numberOfSeats = 34, division = lo2)
        p20 = createPlace(name = "20", numberOfSeats = 34, preferredSubjects = setOf(geografia), division = lo2)
        p21 = createPlace(name = "21", numberOfSeats = 34, division = lo2)
        p22 = createPlace(name = "22", numberOfSeats = 34, preferredSubjects = setOf(matematyka), preferredTeachers = setOf(czuba), preferredDivisions = setOf(class1a), division = lo2)
        p24 = createPlace(name = "24", numberOfSeats = 34, preferredSubjects = setOf(chemia), division = lo2)
        p25 = createPlace(name = "25", numberOfSeats = 34, division = lo2)
        p31 = createPlace(name = "31", numberOfSeats = 34, preferredSubjects = setOf(edukacjaDoBezpieczeństwa), division = lo2)
        p35 = createPlace(name = "35", numberOfSeats = 34, division = lo2)
        p36 = createPlace(name = "36", numberOfSeats = 34, division = lo2)
        pds = createPlace(name = "Duża Sala", shortName = "DS", numberOfSeats = 180, division = lo2)
        pS = createPlace(name = "Siłownia", shortName = "S", numberOfSeats = 34, division = lo2)
        pb = createPlace(name = "B", shortName = "B", numberOfSeats = 180, division = lo2)
        ph = createPlace(name = "H", shortName = "H", numberOfSeats = 180, division = lo2)
        pG4 = createPlace(name = "Gimnazjum 4", shortName = "G4", numberOfSeats = 32, division = lo2)


        // =====================================================
        // Period Interval
        // =====================================================
        interval = IntervalDTO(startDate = LocalDate.parse("2018-02-26"), endDate = LocalDate.parse("2018-07-01"), included = true)
        semestLetniPeriod = PeriodDTO(name = "Semestr letni 2018", intervalTimes = setOf(interval), divisionOwnerId = lo2.id)
        semestLetniPeriod = periodService.save(semestLetniPeriod)

        // =====================================================
        // Timetable
        // =====================================================
        // Klasa 1a
        d1aMon2 = createTimetable(dayOfWeek = 1, lesson = l2, subject = biologia, teacher = szarłowicz, place = p8, division = class1a, period = semestLetniPeriod)
        d1aMon3 = createTimetable(dayOfWeek = 1, lesson = l3, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, place = p35, division = class1a, period = semestLetniPeriod)
        d1aMon4a = createTimetable(dayOfWeek = 1, lesson = l4, subject = jAngielski, teacher = kijowska, place = p7g, division = div1aG1, period = semestLetniPeriod)
        d1aMon4b = createTimetable(dayOfWeek = 1, lesson = l4, subject = jAngielski, teacher = kasprzyk, place = p21, division = div1aG2, period = semestLetniPeriod)
        d1aMon5 = createTimetable(dayOfWeek = 1, lesson = l5, subject = religia, teacher = tPiwinski, place = p36, division = class1a, period = semestLetniPeriod)
        d1aMon6 = createTimetable(dayOfWeek = 1, lesson = l6, subject = fizyka, teacher = szott, place = p15, division = class1a, period = semestLetniPeriod)
        d1aMon8 = createTimetable(dayOfWeek = 1, lesson = l8, subject = historia, teacher = świstak, place = p7, division = class1a, period = semestLetniPeriod)
        d1aMon9Test = createTimetable(dayOfWeek = 1, lesson = l9, subject = historia, teacher = świstak, place = p7, division = class1a, period = semestLetniPeriod, everyWeek = 2)

        d1aTue1 = createTimetable(dayOfWeek = 2, lesson = l1, subject = jPolski, teacher = pernal, place = p24, division = class1a, period = semestLetniPeriod)
        d1aTue2 = createTimetable(dayOfWeek = 2, lesson = l2, subject = geografia, teacher = gonet, place = p20, division = class1a, period = semestLetniPeriod)
        d1aTue3a = createTimetable(dayOfWeek = 2, lesson = l3, subject = jAngielski, teacher = kijowska, place = p4, division = div1aG1, period = semestLetniPeriod)
        d1aTue3b = createTimetable(dayOfWeek = 2, lesson = l3, subject = jAngielski, teacher = kasprzyk, place = p7g, division = div1aG2, period = semestLetniPeriod)
        d1aTue4 = createTimetable(dayOfWeek = 2, lesson = l4, subject = matematyka, teacher = czuba, place = p22, division = class1a, period = semestLetniPeriod)
        d1aTue5 = createTimetable(dayOfWeek = 2, lesson = l5, subject = matematyka, teacher = czuba, place = p22, division = class1a, period = semestLetniPeriod)
        d1aTue6 = createTimetable(dayOfWeek = 2, lesson = l6, subject = chemia, teacher = chodorowiczBąk, place = p24, division = class1a, period = semestLetniPeriod)
        d1aTue7 = createTimetable(dayOfWeek = 2, lesson = l7, subject = fizyka, teacher = szott, place = p15, division = class1a, period = semestLetniPeriod)

        d1aWen1 = createTimetable(dayOfWeek = 3, lesson = l1, subject = wiedzaOSpołeczeństwie, teacher = świstak, place = p24, division = class1a, period = semestLetniPeriod)
        d1aWen3 = createTimetable(dayOfWeek = 3, lesson = l3, subject = historia, teacher = świstak, place = p24, division = class1a, period = semestLetniPeriod)
        d1aWen4 = createTimetable(dayOfWeek = 3, lesson = l4, subject = wychowaniedoZyciaWRodzinie, teacher = solecki, place = p35, division = class1a, period = semestLetniPeriod)

        d1aThu1 = createTimetable(dayOfWeek = 4, lesson = l1, subject = podstawyPrzedsiębiorczości, teacher = mSuchodolski, place = p36, division = class1a, period = semestLetniPeriod)
        d1aThu2 = createTimetable(dayOfWeek = 4, lesson = l2, subject = matematyka, teacher = czuba, place = p22, division = class1a, period = semestLetniPeriod)
        d1aThu3 = createTimetable(dayOfWeek = 4, lesson = l3, subject = wiedzaOKulturze, teacher = twardzikWilk, place = p13, division = class1a, period = semestLetniPeriod)
        d1aThu4 = createTimetable(dayOfWeek = 4, lesson = l4, subject = edukacjaDoBezpieczeństwa, teacher = bloch, place = p31, division = class1a, period = semestLetniPeriod)
        d1aThu5a = createTimetable(dayOfWeek = 4, lesson = l5, subject = jAngielski, teacher = kijowska, place = p14, division = div1aG1, period = semestLetniPeriod)
        d1aThu5b = createTimetable(dayOfWeek = 4, lesson = l5, subject = informatyka, teacher = dzierwa, place = p4, division = div1aG2, period = semestLetniPeriod)
        d1aThu6a = createTimetable(dayOfWeek = 4, lesson = l6, subject = informatyka, teacher = dzierwa, place = p4, division = div1aG1, period = semestLetniPeriod)
        d1aThu6b = createTimetable(dayOfWeek = 4, lesson = l6, subject = jAngielski, teacher = kasprzyk, place = p5, division = div1aG2, period = semestLetniPeriod)

        d1aThu7 = createTimetable(dayOfWeek = 4, lesson = l7, subject = godzWych, teacher = czuba, place = p22, division = class1a, period = semestLetniPeriod)

        d1aFri4 = createTimetable(dayOfWeek = 5, lesson = l4, subject = jPolski, teacher = pernal, place = p16, division = class1a, period = semestLetniPeriod)
        d1aFri5 = createTimetable(dayOfWeek = 5, lesson = l5, subject = jPolski, teacher = pernal, place = p16, division = class1a, period = semestLetniPeriod)
        d1aFri6 = createTimetable(dayOfWeek = 5, lesson = l6, subject = matematyka, teacher = czuba, place = p36, division = class1a, period = semestLetniPeriod)
        d1aFri8 = createTimetable(dayOfWeek = 5, lesson = l8, subject = religia, teacher = tPiwinski, place = p22, division = class1a, period = semestLetniPeriod)

        d1aMon7a = createTimetable(dayOfWeek = 1, lesson = l7, subject = jNiemiecki, teacher = hadel, place = p14, division = divNie1G2, period = semestLetniPeriod)
        d1aMon7b = createTimetable(dayOfWeek = 1, lesson = l7, subject = jFrancuski, teacher = rachwał, place = p25, division = divFra1G1, period = semestLetniPeriod)
        d1aMon7c = createTimetable(dayOfWeek = 1, lesson = l7, subject = jRosyjski, teacher = łopuszańska, place = p6, division = divRos1G1, period = semestLetniPeriod)
        d1aMon7d = createTimetable(dayOfWeek = 1, lesson = l7, subject = jRosyjski, teacher = stasik, place = p10, division = divRos1G2, period = semestLetniPeriod)

        d1aWen2a = createTimetable(dayOfWeek = 3, lesson = l2, subject = jNiemiecki, teacher = hadel, place = p14, division = divNie1G2, period = semestLetniPeriod)
        d1vWen2b = createTimetable(dayOfWeek = 3, lesson = l2, subject = jFrancuski, teacher = rachwał, place = p13, division = divFra1G1, period = semestLetniPeriod)
        d1aWen2c = createTimetable(dayOfWeek = 3, lesson = l2, subject = jRosyjski, teacher = łopuszańska, place = pG4, division = divRos1G1, period = semestLetniPeriod)
        d1aWen2d = createTimetable(dayOfWeek = 3, lesson = l2, subject = jRosyjski, teacher = stasik, place = p12, division = divRos1G2, period = semestLetniPeriod)

        d1aFri7a = createTimetable(dayOfWeek = 5, lesson = l7, subject = jNiemiecki, teacher = hadel, place = p14, division = divNie1G2, period = semestLetniPeriod)
        d1vFri7b = createTimetable(dayOfWeek = 5, lesson = l7, subject = jFrancuski, teacher = rachwał, place = p24, division = divFra1G1, period = semestLetniPeriod)
        d1aFri7c = createTimetable(dayOfWeek = 5, lesson = l7, subject = jRosyjski, teacher = łopuszańska, place = p6, division = divRos1G1, period = semestLetniPeriod)
        d1aFri7d = createTimetable(dayOfWeek = 5, lesson = l7, subject = jRosyjski, teacher = stasik, place = p13, division = divRos1G2, period = semestLetniPeriod)

        d1WfGrDz1Fri2 = createTimetable(dayOfWeek = 5, lesson = l2, subject = wychowanieFizyczne, teacher = wilk, place = ph, division = div1GrDz1, period = semestLetniPeriod)
        d1WfGrCh1Fri2 = createTimetable(dayOfWeek = 5, lesson = l2, subject = wychowanieFizyczne, teacher = zając, place = ph, division = div1GrCh1, period = semestLetniPeriod)
        d1WfGrCh2Fri2 = createTimetable(dayOfWeek = 5, lesson = l2, subject = wychowanieFizyczne, teacher = dawidkoJ, place = ph, division = div1GrCh2, period = semestLetniPeriod)

        d1WfGrDz1Fri3 = createTimetable(dayOfWeek = 5, lesson = l3, subject = wychowanieFizyczne, teacher = wilk, place = ph, division = div1GrDz1, period = semestLetniPeriod)
        d1WfGrDz2Fri3 = createTimetable(dayOfWeek = 5, lesson = l3, subject = wychowanieFizyczne, teacher = rachfał, place = pS, division = div1GrDz2, period = semestLetniPeriod)
        d1WfGrCh1Fri3 = createTimetable(dayOfWeek = 5, lesson = l3, subject = wychowanieFizyczne, teacher = zając, place = ph, division = div1GrCh1, period = semestLetniPeriod)
        d1WfGrCh2Fri3 = createTimetable(dayOfWeek = 5, lesson = l3, subject = wychowanieFizyczne, teacher = dawidkoJ, place = ph, division = div1GrCh2, period = semestLetniPeriod)

        // Calass 1B
        d1bMon2 = createTimetable(dayOfWeek = 1, lesson = l2, subject = podstawyPrzedsiębiorczości, teacher = mSuchodolski, place = p10, division = class1b, period = semestLetniPeriod)
        d1bMon3 = createTimetable(dayOfWeek = 1, lesson = l3, subject = jPolski, teacher = klein, place = p13, division = class1b, period = semestLetniPeriod)
        d1bMon4 = createTimetable(dayOfWeek = 1, lesson = l4, subject = jPolski, teacher = klein, place = p13, division = class1b, period = semestLetniPeriod)
        d1bMon5 = createTimetable(dayOfWeek = 1, lesson = l5, subject = edukacjaDoBezpieczeństwa, teacher = bloch, place = p31, division = class1b, period = semestLetniPeriod)
        d1bMon6 = createTimetable(dayOfWeek = 1, lesson = l6, subject = historia, teacher = świstak, place = p35, division = class1b, period = semestLetniPeriod)
        d1bMon7a = createTimetable(dayOfWeek = 1, lesson = l7, subject = jNiemiecki, teacher = przybyłowiczCiszewska, place = p13, division = divNie1G1, period = semestLetniPeriod)
        d1bMon8 = createTimetable(dayOfWeek = 1, lesson = l8, subject = geografia, teacher = gonet, place = p20, division = class1b, period = semestLetniPeriod)

        d1bTue5 = createTimetable(dayOfWeek = 2, lesson = l5, subject = wiedzaOSpołeczeństwie, teacher = dynowski, place = p7, division = class1b, period = semestLetniPeriod)
        d1aTue6a = createTimetable(dayOfWeek = 2, lesson = l6, subject = jAngielski, teacher = karnas, place = p35, division = div1bG1, period = semestLetniPeriod)
        d1aTue6b = createTimetable(dayOfWeek = 2, lesson = l6, subject = jAngielski, teacher = kijowska, place = p6, division = div1bG2, period = semestLetniPeriod)
        d1bTue7 = createTimetable(dayOfWeek = 2, lesson = l7, subject = biologia, teacher = szarłowicz, place = p8, division = class1b, period = semestLetniPeriod)
        d1bTue8 = createTimetable(dayOfWeek = 2, lesson = l8, subject = chemia, teacher = chodorowiczBąk, place = p24, division = class1b, period = semestLetniPeriod)
        d1bTue9a = createTimetable(dayOfWeek = 2, lesson = l9, subject = wychowanieFizyczne, teacher = rachfał, place = pds, division = div1Gr1bDz1, period = semestLetniPeriod)
        d1bTue10a = createTimetable(dayOfWeek = 2, lesson = l10, subject = wychowanieFizyczne, teacher = rachfał, place = pds, division = div1Gr1bDz1, period = semestLetniPeriod)

        d1bWen1 = createTimetable(dayOfWeek = 3, lesson = l1, subject = matematyka, teacher = mięsowicz, place = p16, division = class1b, period = semestLetniPeriod)
        d1bWen2a = createTimetable(dayOfWeek = 3, lesson = l2, subject = jNiemiecki, teacher = przybyłowiczCiszewska, place = p25, division = divNie1G1, period = semestLetniPeriod)
        d1bWen3a = createTimetable(dayOfWeek = 3, lesson = l3, subject = jAngielski, teacher = karnas, place = p10, division = div1bG1, period = semestLetniPeriod)
        d1bWen3b = createTimetable(dayOfWeek = 3, lesson = l3, subject = informatyka, teacher = dzierwa, place = p4, division = div1bG2, period = semestLetniPeriod)
        d1bWen4a = createTimetable(dayOfWeek = 3, lesson = l4, subject = informatyka, teacher = dzierwa, place = p4, division = div1bG2, period = semestLetniPeriod)
        d1bWen4b = createTimetable(dayOfWeek = 3, lesson = l4, subject = jAngielski, teacher = kijowska, place = p14, division = div1bG2, period = semestLetniPeriod)
        d1bWen5 = createTimetable(dayOfWeek = 3, lesson = l5, subject = geografia, teacher = gonet, place = p20, division = class1b, period = semestLetniPeriod)
        d1bWen6 = createTimetable(dayOfWeek = 3, lesson = l6, subject = wiedzaOKulturze, teacher = twardzikWilk, place = p13, division = class1b, period = semestLetniPeriod)
        d1bWen7 = createTimetable(dayOfWeek = 3, lesson = l7, subject = godzWych, teacher = klein, place = p16, division = class1b, period = semestLetniPeriod)
        d1bWen8 = createTimetable(dayOfWeek = 3, lesson = l8, subject = religia, teacher = tPiwinski, place = p36, division = class1b, period = semestLetniPeriod)


        d1bThu1 = createTimetable(dayOfWeek = 4, lesson = l1, subject = fizyka, teacher = józefczyk, place = p15, division = class1b, period = semestLetniPeriod)
        d1bThu2 = createTimetable(dayOfWeek = 4, lesson = l2, subject = podstawyPrzedsiębiorczości, teacher = mSuchodolski, place = p14, division = class1b, period = semestLetniPeriod)
        d1bThu3 = createTimetable(dayOfWeek = 4, lesson = l3, subject = matematyka, teacher = mięsowicz, place = p22, division = class1b, period = semestLetniPeriod)
        d1bThu4 = createTimetable(dayOfWeek = 4, lesson = l4, subject = matematyka, teacher = mięsowicz, place = p22, division = class1b, period = semestLetniPeriod)
        d1bThu5 = createTimetable(dayOfWeek = 4, lesson = l5, subject = religia, teacher = tPiwinski, place = p10, division = class1b, period = semestLetniPeriod)
        d1bThu6 = createTimetable(dayOfWeek = 1, lesson = l6, subject = historia, teacher = świstak, place = p36, division = class1b, period = semestLetniPeriod)
        d1bThu7 = createTimetable(dayOfWeek = 1, lesson = l7, subject = wychowaniedoZyciaWRodzinie, teacher = solecki, place = p36, division = class1b, period = semestLetniPeriod)


        d1bFri4 = createTimetable(dayOfWeek = 5, lesson = l4, subject = matematyka, teacher = mięsowicz, place = p7, division = class1b, period = semestLetniPeriod)
        d1bFri5a = createTimetable(dayOfWeek = 5, lesson = l5, subject = jAngielski, teacher = karnas, place = p11, division = div1bG1, period = semestLetniPeriod)
        d1bFri5b = createTimetable(dayOfWeek = 5, lesson = l5, subject = jAngielski, teacher = kijowska, place = p13, division = div1bG2, period = semestLetniPeriod)
        d1bFri6 = createTimetable(dayOfWeek = 5, lesson = l6, subject = jPolski, teacher = klein, place = p15, division = class1b, period = semestLetniPeriod)
        d1bFri7a = createTimetable(dayOfWeek = 5, lesson = l7, subject = jNiemiecki, teacher = przybyłowiczCiszewska, place = p22, division = divNie1G1, period = semestLetniPeriod)

        // =====================================================
        // Curriculum
        // =====================================================
        createCurriculum(division = class1aGenerate, subject = biologia, teacher = szarłowicz, place = p8, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, place = p35, numberOfActivities = 2)
        createCurriculum(division = class1aGenerate, subject = jAngielski, teacher = kasprzyk, place = p21, numberOfActivities = 3)
        createCurriculum(division = class1aGenerate, subject = religia, teacher = tPiwinski, place = p36, numberOfActivities = 2)
        createCurriculum(division = class1aGenerate, subject = fizyka, teacher = szott, place = p15, numberOfActivities = 2)
        createCurriculum(division = class1aGenerate, subject = jNiemiecki, teacher = przybyłowicz, place = p13, numberOfActivities = 3)
        createCurriculum(division = class1aGenerate, subject = historia, teacher = świstak, place = p7, numberOfActivities = 2)
        createCurriculum(division = class1aGenerate, subject = jPolski, teacher = pernal, place = p16, numberOfActivities = 3)
        createCurriculum(division = class1aGenerate, subject = geografia, teacher = gonet, place = p20, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = matematyka, teacher = czuba, place = p22, numberOfActivities = 4)
        createCurriculum(division = class1aGenerate, subject = chemia, teacher = chodorowiczBąk, place = p24, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = wiedzaOSpołeczeństwie, teacher = świstak, place = p24, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = solecki, place = p36, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = wiedzaOKulturze, teacher = twardzikWilk, place = p13, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = edukacjaDoBezpieczeństwa, teacher = bloch, place = p31, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = informatyka, teacher = dzierwa, place = p4, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = godzWych, teacher = czuba, place = p22, numberOfActivities = 1)
        createCurriculum(division = class1aGenerate, subject = wychowanieFizyczne, teacher = wilk, place = pds, numberOfActivities = 2)

    }


    private fun createPlace(name: String, shortName: String? = null, numberOfSeats: Long?, preferredSubjects: Set<SubjectDTO> = emptySet(), preferredTeachers: Set<TeacherDTO> = emptySet(), preferredDivisions: Set<DivisionDTO> = emptySet(), division: DivisionDTO): PlaceDTO {
        val place = PlaceDTO(name = name, shortName = shortName, numberOfSeats = numberOfSeats, preferredSubjects = preferredSubjects, preferredTeachers = preferredTeachers, preferredDivisions = preferredDivisions, divisionOwnerId = division.id)
        return placeService.save(place)
    }

    private fun createSubject(name: String, shortName: String, divisionOwner: DivisionDTO): SubjectDTO {
        val subject = SubjectDTO(name = name, shortName = shortName, divisionOwnerId = divisionOwner.id)
        return subjectService.save(subject)
    }

    private fun createTeacher(degree: String, name: String, surname: String, shortName: String? = null, divisionOwner: DivisionDTO, preferredSubjects: Set<SubjectDTO> = emptySet(), preferenceDataTimeForTeachers: Set<PreferenceDataTimeForTeacherDTO> = emptySet()): TeacherDTO {
        val teacher = TeacherDTO(degree = degree, name = name, surname = surname, divisionOwnerId = divisionOwner.id, preferredSubjects = preferredSubjects, preferenceDataTimeForTeachers = preferenceDataTimeForTeachers)
        return teacherService.save(teacher)
    }

    private fun createLesson(name: String, startTime: String, endTime: String, divisionOwner: DivisionDTO): LessonDTO {
        val lesson = LessonDTO(name = name, divisionOwnerId = divisionOwner.id, startTimeString = startTime, endTimeString = endTime)
        return lessonService.save(lesson)
    }

    private fun createDivision(name: String, shortName: String, divisionType: DivisionType, numberOfPeople: Long, parents: Set<DivisionDTO> = emptySet(), preferredTeachers: Set<TeacherDTO> = emptySet(), preferredSubjects: Set<SubjectDTO> = emptySet()): DivisionDTO {
        val division = DivisionDTO(name = name, shortName = shortName, divisionType = divisionType, numberOfPeople = numberOfPeople, parents = parents, preferredTeachers = preferredTeachers, preferredSubjects = preferredSubjects)
        return divisionService.save(division)
    }

    private fun createCurriculum(subject: SubjectDTO, teacher: TeacherDTO, division: DivisionDTO, place: PlaceDTO, numberOfActivities: Long): CurriculumDTO {
        val curriculum = CurriculumDTO(subjectId = subject.id, teacherId = teacher.id, divisionId = division.id, placeId = place.id, numberOfActivities = numberOfActivities)
        return curriculumService.save(curriculum)
    }

    private fun createTimetable(
        subject: SubjectDTO,
        lesson: LessonDTO,
        teacher: TeacherDTO,
        place: PlaceDTO,
        division: DivisionDTO,
        period: PeriodDTO,
        type: EventType = EventType.LESSON,
        dayOfWeek: Int? = null,
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
            dayOfWeek = dayOfWeek,
            everyWeek = everyWeek
        )
        return timetableService.save(timetable)
    }
}
