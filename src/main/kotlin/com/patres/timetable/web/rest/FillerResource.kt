package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.enumeration.EventType
import com.patres.timetable.service.*
import com.patres.timetable.service.dto.*
import com.patres.timetable.service.dto.preference.*
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
    private var curriculumListService: CurriculumListService,
    private var placeService: PlaceService) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(FillerResource::class.java)
    }

    var lo2 = DivisionDTO()
    var class1a = DivisionDTO()

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
    var class1aGenerate = DivisionDTO()
    var class1bGenerate = DivisionDTO()
    var class1cGenerate = DivisionDTO()
    var class1dGenerate = DivisionDTO()
    var class1eGenerate = DivisionDTO()
    var class1fGenerate = DivisionDTO()
    var class2aGenerate = DivisionDTO()
    var class2bGenerate = DivisionDTO()
    var class2cGenerate = DivisionDTO()
    var class2dGenerate = DivisionDTO()
    var class2eGenerate = DivisionDTO()
    var class2fGenerate = DivisionDTO()
    var class3aGenerate = DivisionDTO()
    var class3bGenerate = DivisionDTO()
    var class3cGenerate = DivisionDTO()
    var class3dGenerate = DivisionDTO()
    var class3eGenerate = DivisionDTO()
    var class3fGenerate = DivisionDTO()
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
    var grRe = TeacherDTO()

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
        l0 = createLesson(name = "0", startTime = "07:10", endTime = "07:55", divisionOwner = lo2)
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
        deptuch = createTeacher(degree = "mgr", name = "Witold", surname = "Deptuch", divisionOwner = lo2)
        janusz = createTeacher(degree = "mgr", name = "Edyta", surname = "Janusz", divisionOwner = lo2)
        stasik = createTeacher(degree = "mgr", name = "Bogusława", surname = "Stasik", divisionOwner = lo2)
        urbanek = createTeacher(degree = "mgr", name = "Jadwiga", surname = "Urbanek", divisionOwner = lo2)
        gierlach = createTeacher(degree = "mgr", name = "Anna", surname = "Gierlach", divisionOwner = lo2)
        klein = createTeacher(degree = "mgr", name = "Lucyna", surname = "Klein", divisionOwner = lo2)
        pernal = createTeacher(degree = "mgr", name = "Renata", surname = "Pernal", divisionOwner = lo2)
        grodeckaZaremba = createTeacher(degree = "mgr", name = "Tamara", surname = "Grodecka-Zaremba", divisionOwner = lo2)
        prajsnar = createTeacher(degree = "mgr", name = "Arkadiusz", surname = "Prajsnar", divisionOwner = lo2)
        dynowski = createTeacher(degree = "mgr", name = "Lucjan", surname = "Dynowski", divisionOwner = lo2)
        longosz = createTeacher(degree = "dr", name = "Elżbieta", surname = "Longosz", divisionOwner = lo2)
        świstak = createTeacher(degree = "mgr", name = "Elżbieta", surname = "Świstak", divisionOwner = lo2)
        baran = createTeacher(degree = "mgr", name = "Elżbieta", surname = "Baran", divisionOwner = lo2)
        karnas = createTeacher(degree = "mgr", name = "Monika", surname = "Karnas", divisionOwner = lo2)
        kasprzyk = createTeacher(degree = "mgr", name = "Tomasz", surname = "Kasprzyk", divisionOwner = lo2)
        kijowska = createTeacher(degree = "mgr", name = "Beata", surname = "Kijowska", divisionOwner = lo2)
        kolanko = createTeacher(degree = "mgr", name = "Irena", surname = "Kolanko", divisionOwner = lo2)
        trybusGorczyca = createTeacher(degree = "mgr", name = "Agnieszka", surname = "Trybus-Gorczyca", divisionOwner = lo2)
        czuba = createTeacher(degree = "mgr", name = "Beata", surname = "Czuba", divisionOwner = lo2)
        jastrzębska = createTeacher(degree = "mgr", name = "Mariola", surname = "Jastrzębska", divisionOwner = lo2)
        mięsowicz = createTeacher(degree = "mgr", name = "Jolanta", surname = "Mięsowicz", divisionOwner = lo2)
        hadel = createTeacher(degree = "mgr", name = "Anna", surname = "Hadel", divisionOwner = lo2)
        przybyłowicz = createTeacher(degree = "mgr", name = "Anna", surname = "Przybyłowicz", divisionOwner = lo2)
        przybyłowiczCiszewska = createTeacher(degree = "mgr", name = "Katarzyna", surname = "Przybyłowicz-Ciszewska", divisionOwner = lo2)
        rachwał = createTeacher(degree = "mgr", name = "Beata", surname = "Rachwał", divisionOwner = lo2)
        chodorowiczBąk = createTeacher(degree = "dr", name = "Agata", surname = "Chodorowicz-Bąk", divisionOwner = lo2)
        serwatka = createTeacher(degree = "mgr", name = "Monika", surname = "Serwatka", divisionOwner = lo2)
        gonet = createTeacher(degree = "mgr", name = "Tatiana", surname = "Gonet", divisionOwner = lo2)
        guzik = createTeacher(degree = "mgr", name = "Maciej", surname = "Guzik", divisionOwner = lo2)
        szarłowicz = createTeacher(degree = "mgr", name = "Tomasz", surname = "Szarłowicz", divisionOwner = lo2)
        dawidkoJ = createTeacher(degree = "mgr", name = "Jacek", surname = "Dawidko", divisionOwner = lo2)
        wilk = createTeacher(degree = "mgr", name = "Wojciech", surname = "Wilk", divisionOwner = lo2)
        zając = createTeacher(degree = "mgr", name = "Tomasz", surname = "Zając", divisionOwner = lo2)
        dawidkoR = createTeacher(degree = "mgr", name = "Renata", surname = "Dawidko", divisionOwner = lo2)
        rachfał = createTeacher(degree = "mgr", name = "Maria", surname = "Rachfał", divisionOwner = lo2)
        bloch = createTeacher(degree = "mgr inż	", name = "Sławomir", surname = "Bloch", divisionOwner = lo2)
        józefczyk = createTeacher(degree = "mgr", name = "Stanisław", surname = "Józefczyk", divisionOwner = lo2)
        szott = createTeacher(degree = "mgr", name = "Irma", surname = "Szott", divisionOwner = lo2)
        dzierwa = createTeacher(degree = "mgr", name = "Małgorzata", surname = "Dzierwa", divisionOwner = lo2)
        solecki = createTeacher(degree = "mgr", name = "Ryszard", surname = "Solecki", divisionOwner = lo2)
        kudroń = createTeacher(degree = "mgr", name = "Katarzyna", surname = "Kudroń", divisionOwner = lo2)
        pPiernal = createTeacher(degree = "ks	 mgr", name = "Pernal", surname = "Piotr", divisionOwner = lo2)
        tPiwinski = createTeacher(degree = "ks	 mgr", name = "Piwiński", surname = "Tadeusz", divisionOwner = lo2)
        matwiej = createTeacher(degree = "mgr", name = "Wojciech", surname = "Matwiej", divisionOwner = lo2)
        twardzikWilk = createTeacher(degree = "mgr", name = "Małgorzata", surname = "Twardzik-Wilk", divisionOwner = lo2)
        suchodolski = createTeacher(degree = "mgr inż	", name = "Zbigniew", surname = "Suchodolski", divisionOwner = lo2)
        mSuchodolski = createTeacher(degree = "mgr", name = "Mateusz", surname = "Suchodolski", divisionOwner = lo2)
        łopuszańska = createTeacher(degree = "mgr", name = "Dorota ", surname = "Łopuszańska-Patrylak", divisionOwner = lo2)

        grRe = createTeacher(degree = "", name = "Re", surname = "Gr", divisionOwner = lo2) //Nie znam skrót

        // =====================================================
        // Division
        // =====================================================
        class1a = createDivision(name = "1 A", shortName = "1 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1b = createDivision(name = "1 B", shortName = "1 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1c = createDivision(name = "1 C", shortName = "1 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1d = createDivision(name = "1 D", shortName = "1 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1e = createDivision(name = "1 E", shortName = "1 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1f = createDivision(name = "1 F", shortName = "1 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2a = createDivision(name = "2 A", shortName = "1 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2b = createDivision(name = "2 B", shortName = "2 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2c = createDivision(name = "2 C", shortName = "2 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2d = createDivision(name = "2 D", shortName = "2 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2e = createDivision(name = "2 E", shortName = "2 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2f = createDivision(name = "2 F", shortName = "2 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3a = createDivision(name = "3 A", shortName = "3 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3b = createDivision(name = "3 B", shortName = "3 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3c = createDivision(name = "3 C", shortName = "3 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3d = createDivision(name = "3 D", shortName = "3 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3e = createDivision(name = "3 E", shortName = "3 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3f = createDivision(name = "3 F", shortName = "3 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        div1aG1 = createDivision(name = "1A gr	 1", shortName = "Ang 1A gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1a))
        div1aG2 = createDivision(name = "1A gr	 2", shortName = "Ang 1A gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1a))
        divNie1G1 = createDivision(name = "Niemiecki 1 gr	 1", shortName = "Niem 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1a))
        divNie1G2 = createDivision(name = "Niemiecki 1 gr	 2", shortName = "Niem 1 gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1b))
        divFra1G1 = createDivision(name = "Francuski 1 gr	 1", shortName = "Fra 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1a))
        divRos1G1 = createDivision(name = "Rosyjski 1 gr	 1", shortName = "Ros 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1a))
        divRos1G2 = createDivision(name = "Rosyjski 1 gr	 2", shortName = "Ros 1 gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1a))
        div1GrCh1 = createDivision(name = "WF 1 gr	 Chłopcy 1", shortName = "WF 1 gr	 Ch 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1a))
        div1GrCh2 = createDivision(name = "WF 1 gr	 Chłopcy 2", shortName = "WF 1 gr	 Ch 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1b, class1c))
        div1GrDz1 = createDivision(name = "WF 1 gr	 Dziewczyny 1", shortName = "WF 1 gr	 Dz 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1a, class1c))
        div1GrDz2 = createDivision(name = "WF 1 gr	 Dziewczyny 2", shortName = "WF 1 gr	 Dz 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1c))
        div1bG1 = createDivision(name = "1B gr	 1", shortName = "Ang 1B gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1b))
        div1bG2 = createDivision(name = "1B gr	 2", shortName = "Ang 1B gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1b))
        div1Gr1bDz1 = createDivision(name = "WF 1 gr	 Dziewczyny 1", shortName = "WF 1 gr	 Dz 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, parents = hashSetOf(class1b))


        class1aGenerate = createDivision(name = "1 A generate", shortName = "1 A G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1bGenerate = createDivision(name = "1 B generate", shortName = "1 B G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1cGenerate = createDivision(name = "1 C generate", shortName = "1 C G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1dGenerate = createDivision(name = "1 D generate", shortName = "1 D G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1eGenerate = createDivision(name = "1 E generate", shortName = "1 E G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class1fGenerate = createDivision(name = "1 F generate", shortName = "1 F G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))

        class2aGenerate = createDivision(name = "2 A generate", shortName = "2 A G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2bGenerate = createDivision(name = "2 B generate", shortName = "2 B G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2cGenerate = createDivision(name = "2 C generate", shortName = "2 C G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2dGenerate = createDivision(name = "2 D generate", shortName = "2 D G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2eGenerate = createDivision(name = "2 E generate", shortName = "2 E G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class2fGenerate = createDivision(name = "2 F generate", shortName = "2 F G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))

        class3aGenerate = createDivision(name = "3 A generate", shortName = "3 A G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3bGenerate = createDivision(name = "3 B generate", shortName = "3 B G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3cGenerate = createDivision(name = "3 C generate", shortName = "3 C G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3dGenerate = createDivision(name = "3 D generate", shortName = "3 D G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3eGenerate = createDivision(name = "3 E generate", shortName = "3 E G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))
        class3fGenerate = createDivision(name = "3 F generate", shortName = "3 F G", divisionType = DivisionType.CLASS, numberOfPeople = 32, parents = hashSetOf(lo2))

        // =====================================================
        // Place
        // =====================================================
        p4 = createPlace(name = "4", numberOfSeats = 34, division = lo2)
        p5 = createPlace(name = "5", numberOfSeats = 34, division = lo2)
        p6 = createPlace(name = "6", numberOfSeats = 34, division = lo2)
        p7 = createPlace(name = "7", numberOfSeats = 34, division = lo2)
        p7g = createPlace(name = "7g", numberOfSeats = 16, division = lo2)
        p8 = createPlace(name = "8", numberOfSeats = 34, division = lo2)
        p10 = createPlace(name = "10", numberOfSeats = 34, division = lo2)
        p11 = createPlace(name = "11", numberOfSeats = 34, division = lo2)
        p12 = createPlace(name = "12", numberOfSeats = 34, division = lo2)
        p13 = createPlace(name = "13", numberOfSeats = 34, division = lo2)
        p14 = createPlace(name = "14", numberOfSeats = 34, division = lo2)
        p15 = createPlace(name = "15", numberOfSeats = 34, division = lo2)
        p16 = createPlace(name = "16", numberOfSeats = 34, division = lo2)
        p20 = createPlace(name = "20", numberOfSeats = 34, division = lo2)
        p21 = createPlace(name = "21", numberOfSeats = 34, division = lo2)
        p22 = createPlace(name = "22", numberOfSeats = 34, division = lo2)
        p24 = createPlace(name = "24", numberOfSeats = 34, division = lo2)
        p25 = createPlace(name = "25", numberOfSeats = 34, division = lo2)
        p31 = createPlace(name = "31", numberOfSeats = 34, division = lo2)
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
        interval = IntervalDTO(startDate = LocalDate.parse("2018-02-26"), endDate = LocalDate.parse("2018-07-01"), includedState = true)
        semestLetniPeriod = PeriodDTO(name = "Semestr letni 2018", intervalTimes = hashSetOf(interval), divisionOwnerId = lo2.id)
        semestLetniPeriod = periodService.save(semestLetniPeriod)

        // =====================================================
        // Timetable
        // =====================================================
        // Klasa 1a
        /*
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
*/
        // =====================================================
        // Curriculum
        // =====================================================

        val curriculum1aBiologia = createCurriculum(division = class1aGenerate, subject = biologia, teacher = szarłowicz, numberOfActivities = 1)
        val curriculum1aPodstawyPrzedsiębiorczości = createCurriculum(division = class1aGenerate, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, numberOfActivities = 2)
        val curriculum1aJAngielski = createCurriculum(division = class1aGenerate, subject = jAngielski, teacher = kasprzyk, numberOfActivities = 3)
        val curriculum1aReligia = createCurriculum(division = class1aGenerate, subject = religia, teacher = tPiwinski, numberOfActivities = 2)
        val curriculum1aFizyka = createCurriculum(division = class1aGenerate, subject = fizyka, teacher = szott, numberOfActivities = 2)
        val curriculum1aJNiemiecki = createCurriculum(division = class1aGenerate, subject = jNiemiecki, teacher = przybyłowicz, numberOfActivities = 3)
        val curriculum1aHistoria = createCurriculum(division = class1aGenerate, subject = historia, teacher = świstak, numberOfActivities = 2)
        val curriculum1aJPolski = createCurriculum(division = class1aGenerate, subject = jPolski, teacher = pernal, numberOfActivities = 3)
        val curriculum1aGeografia = createCurriculum(division = class1aGenerate, subject = geografia, teacher = gonet, numberOfActivities = 1)
        val curriculum1aMatematyka = createCurriculum(division = class1aGenerate, subject = matematyka, teacher = czuba, numberOfActivities = 4)
        val curriculum1aChemia = createCurriculum(division = class1aGenerate, subject = chemia, teacher = chodorowiczBąk, numberOfActivities = 1)
        val curriculum1aWiedzaOSpołeczeństwie = createCurriculum(division = class1aGenerate, subject = wiedzaOSpołeczeństwie, teacher = świstak, numberOfActivities = 1)
        val curriculum1aWychowaniedoZyciaWRodzinie = createCurriculum(division = class1aGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = solecki, numberOfActivities = 1)
        val curriculum1aWiedzaOKulturze = createCurriculum(division = class1aGenerate, subject = wiedzaOKulturze, teacher = twardzikWilk, numberOfActivities = 1)
        val curriculum1aEdukacjaDoBezpieczeństwa = createCurriculum(division = class1aGenerate, subject = edukacjaDoBezpieczeństwa, teacher = bloch, numberOfActivities = 1)
        val curriculum1aInformatyka = createCurriculum(division = class1aGenerate, subject = informatyka, teacher = dzierwa, numberOfActivities = 1)
        val curriculum1aGodzWych = createCurriculum(division = class1aGenerate, subject = godzWych, teacher = czuba, numberOfActivities = 1)
        val curriculum1aWychowanieFizyczne = createCurriculum(division = class1aGenerate, subject = wychowanieFizyczne, teacher = wilk, numberOfActivities = 2)

        val curriculum1bPodstawyPrzedsiębiorczości = createCurriculum(division = class1bGenerate, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, numberOfActivities = 2)
        val curriculum1bJPolski = createCurriculum(division = class1bGenerate, subject = jPolski, teacher = klein, numberOfActivities = 3)
        val curriculum1bEdukacjaDoBezpieczeństwa = createCurriculum(division = class1bGenerate, subject = edukacjaDoBezpieczeństwa, teacher = bloch, numberOfActivities = 1)
        val curriculum1bJdodatkowy = createCurriculum(division = class1bGenerate, subject = jNiemiecki, teacher = przybyłowiczCiszewska, numberOfActivities = 3)
        val curriculum1bGeografia = createCurriculum(division = class1bGenerate, subject = geografia, teacher = gonet, numberOfActivities = 2)
        val curriculum1bWiedzaOSpołeczeństwie = createCurriculum(division = class1bGenerate, subject = wiedzaOSpołeczeństwie, teacher = dynowski, numberOfActivities = 1)
        val curriculum1bJAngielski = createCurriculum(division = class1bGenerate, subject = jAngielski, teacher = kasprzyk, numberOfActivities = 3)
        val curriculum1bInformatyka = createCurriculum(division = class1bGenerate, subject = informatyka, teacher = dzierwa, numberOfActivities = 1)
        val curriculum1bChemia = createCurriculum(division = class1bGenerate, subject = chemia, teacher = chodorowiczBąk, numberOfActivities = 1)
        val curriculum1bWychowanieFizyczne = createCurriculum(division = class1bGenerate, subject = wychowanieFizyczne, teacher = rachfał, numberOfActivities = 2)
        val curriculum1bMatematyka = createCurriculum(division = class1bGenerate, subject = matematyka, teacher = mięsowicz, numberOfActivities = 4)
        val curriculum1bWiedzaOKulturze = createCurriculum(division = class1bGenerate, subject = wiedzaOKulturze, teacher = twardzikWilk, numberOfActivities = 1)
        val curriculum1bGodzWych = createCurriculum(division = class1bGenerate, subject = godzWych, teacher = klein, numberOfActivities = 1)
        val curriculum1bReligia = createCurriculum(division = class1bGenerate, subject = religia, teacher = tPiwinski, numberOfActivities = 2)
        val curriculum1bFizyka = createCurriculum(division = class1bGenerate, subject = fizyka, teacher = józefczyk, numberOfActivities = 1)
        val curriculum1bWychowaniedoZyciaWRodzinie = createCurriculum(division = class1bGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = solecki, numberOfActivities = 1)
        val curriculum1bBiologia = createCurriculum(division = class1bGenerate, subject = biologia, teacher = szarłowicz, numberOfActivities = 1)
        val curriculum1bHistoria = createCurriculum(division = class1bGenerate, subject = historia, teacher = świstak, numberOfActivities = 2)

        val curriculum1cBiologia = createCurriculum(division = class1cGenerate, subject = biologia, teacher = deptuch, numberOfActivities = 1)
        val curriculum1cChemia = createCurriculum(division = class1cGenerate, subject = chemia, teacher = chodorowiczBąk, numberOfActivities = 1)
        val curriculum1cEdukacjaDoBezpieczeństwa = createCurriculum(division = class1cGenerate, subject = edukacjaDoBezpieczeństwa, teacher = bloch, numberOfActivities = 1)
        val curriculum1cFizyka = createCurriculum(division = class1cGenerate, subject = fizyka, teacher = józefczyk, numberOfActivities = 1)
        val curriculum1cGeografia = createCurriculum(division = class1cGenerate, subject = geografia, teacher = gonet, numberOfActivities = 2)
        val curriculum1cGodzWych = createCurriculum(division = class1cGenerate, subject = godzWych, teacher = bloch, numberOfActivities = 1)
        val curriculum1cHistoria = createCurriculum(division = class1cGenerate, subject = historia, teacher = dynowski, numberOfActivities = 3)
        val curriculum1cInformatyka = createCurriculum(division = class1cGenerate, subject = informatyka, teacher = dzierwa, numberOfActivities = 1)
        val curriculum1cJAngielski = createCurriculum(division = class1cGenerate, subject = jAngielski, teacher = kolanko, numberOfActivities = 3)
        val curriculum1cJdodatkowy = createCurriculum(division = class1cGenerate, subject = jRosyjski, teacher = stasik, numberOfActivities = 3)
        val curriculum1cJPolski = createCurriculum(division = class1cGenerate, subject = jPolski, teacher = klein, numberOfActivities = 4)
        val curriculum1cMatematyka = createCurriculum(division = class1cGenerate, subject = matematyka, teacher = józefczyk, numberOfActivities = 3)
        val curriculum1cPodstawyPrzedsiębiorczości = createCurriculum(division = class1cGenerate, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, numberOfActivities = 2)
        val curriculum1cReligia = createCurriculum(division = class1cGenerate, subject = religia, teacher = tPiwinski, numberOfActivities = 2)
        val curriculum1cWiedzaOKulturze = createCurriculum(division = class1cGenerate, subject = wiedzaOKulturze, teacher = twardzikWilk, numberOfActivities = 1)
        val curriculum1cWiedzaOSpołeczeństwie = createCurriculum(division = class1cGenerate, subject = wiedzaOSpołeczeństwie, teacher = dynowski, numberOfActivities = 1)
        val curriculum1cWychowaniedoZyciaWRodzinie = createCurriculum(division = class1cGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = solecki, numberOfActivities = 1)
        val curriculum1cWychowanieFizyczne = createCurriculum(division = class1cGenerate, subject = wychowanieFizyczne, teacher = dawidkoJ, numberOfActivities = 2)


        val curriculum1dBiologia = createCurriculum(division = class1dGenerate, subject = biologia, teacher = deptuch, numberOfActivities = 1)
        val curriculum1dChemia = createCurriculum(division = class1dGenerate, subject = chemia, teacher = chodorowiczBąk, numberOfActivities = 1)
        val curriculum1dEdukacjaDoBezpieczeństwa = createCurriculum(division = class1dGenerate, subject = edukacjaDoBezpieczeństwa, teacher = bloch, numberOfActivities = 1)
        val curriculum1dFizyka = createCurriculum(division = class1dGenerate, subject = fizyka, teacher = józefczyk, numberOfActivities = 1)
        val curriculum1dGeografia = createCurriculum(division = class1dGenerate, subject = geografia, teacher = gonet, numberOfActivities = 2)
        val curriculum1dGodzWych = createCurriculum(division = class1dGenerate, subject = godzWych, teacher = pernal, numberOfActivities = 1)
        val curriculum1dHistoria = createCurriculum(division = class1dGenerate, subject = historia, teacher = świstak, numberOfActivities = 3)
        val curriculum1dInformatyka = createCurriculum(division = class1dGenerate, subject = informatyka, teacher = dzierwa, numberOfActivities = 1)
        val curriculum1dJAngielski = createCurriculum(division = class1dGenerate, subject = jAngielski, teacher = karnas, numberOfActivities = 3)
        val curriculum1dJdodatkowy = createCurriculum(division = class1dGenerate, subject = jNiemiecki, teacher = hadel, numberOfActivities = 3)
        val curriculum1dJPolski = createCurriculum(division = class1dGenerate, subject = jPolski, teacher = pernal, numberOfActivities = 4)
        val curriculum1dMatematyka = createCurriculum(division = class1dGenerate, subject = matematyka, teacher = józefczyk, numberOfActivities = 3)
        val curriculum1dPodstawyPrzedsiębiorczości = createCurriculum(division = class1dGenerate, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, numberOfActivities = 2)
        val curriculum1dReligia = createCurriculum(division = class1dGenerate, subject = religia, teacher = pPiernal, numberOfActivities = 2)
        val curriculum1dWiedzaOKulturze = createCurriculum(division = class1dGenerate, subject = wiedzaOKulturze, teacher = twardzikWilk, numberOfActivities = 1)
        val curriculum1dWiedzaOSpołeczeństwie = createCurriculum(division = class1dGenerate, subject = wiedzaOSpołeczeństwie, teacher = świstak, numberOfActivities = 1)
        val curriculum1dWychowanieFizyczne = createCurriculum(division = class1dGenerate, subject = wychowanieFizyczne, teacher = dawidkoJ, numberOfActivities = 2)

        val curriculum1eBiologia = createCurriculum(division = class1eGenerate, subject = biologia, teacher = szarłowicz, numberOfActivities = 2)
        val curriculum1eChemia = createCurriculum(division = class1eGenerate, subject = chemia, teacher = serwatka, numberOfActivities = 2)
        val curriculum1eEdukacjaDoBezpieczeństwa = createCurriculum(division = class1eGenerate, subject = edukacjaDoBezpieczeństwa, teacher = bloch, numberOfActivities = 1)
        val curriculum1eFizyka = createCurriculum(division = class1eGenerate, subject = fizyka, teacher = józefczyk, numberOfActivities = 1)
        val curriculum1eGeografia = createCurriculum(division = class1eGenerate, subject = geografia, teacher = gonet, numberOfActivities = 2)
        val curriculum1eGodzWych = createCurriculum(division = class1eGenerate, subject = godzWych, teacher = jastrzębska, numberOfActivities = 1)
        val curriculum1eHistoria = createCurriculum(division = class1eGenerate, subject = historia, teacher = dynowski, numberOfActivities = 3)
        val curriculum1eInformatyka = createCurriculum(division = class1eGenerate, subject = informatyka, teacher = dzierwa, numberOfActivities = 1)
        val curriculum1eJAngielski = createCurriculum(division = class1eGenerate, subject = jAngielski, teacher = kijowska, numberOfActivities = 3)
        val curriculum1eJdodatkowy = createCurriculum(division = class1eGenerate, subject = jFrancuski, teacher = rachwał, numberOfActivities = 3)
        val curriculum1eJPolski = createCurriculum(division = class1eGenerate, subject = jPolski, teacher = pernal, numberOfActivities = 3)
        val curriculum1eMatematyka = createCurriculum(division = class1eGenerate, subject = matematyka, teacher = prajsnar, numberOfActivities = 3)
        val curriculum1ePodstawyPrzedsiębiorczości = createCurriculum(division = class1eGenerate, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, numberOfActivities = 2)
        val curriculum1eReligia = createCurriculum(division = class1eGenerate, subject = religia, teacher = pPiernal, numberOfActivities = 2)
        val curriculum1eWiedzaOKulturze = createCurriculum(division = class1eGenerate, subject = wiedzaOKulturze, teacher = twardzikWilk, numberOfActivities = 1)
        val curriculum1eWiedzaOSpołeczeństwie = createCurriculum(division = class1eGenerate, subject = wiedzaOSpołeczeństwie, teacher = dynowski, numberOfActivities = 1)
        val curriculum1eWychowanieFizyczne = createCurriculum(division = class1eGenerate, subject = wychowanieFizyczne, teacher = dawidkoR, numberOfActivities = 2)

        val curriculum1fBiologia = createCurriculum(division = class1fGenerate, subject = biologia, teacher = szarłowicz, numberOfActivities = 2)
        val curriculum1fChemia = createCurriculum(division = class1fGenerate, subject = chemia, teacher = chodorowiczBąk, numberOfActivities = 2)
        val curriculum1fEdukacjaDoBezpieczeństwa = createCurriculum(division = class1fGenerate, subject = edukacjaDoBezpieczeństwa, teacher = bloch, numberOfActivities = 1)
        val curriculum1fFizyka = createCurriculum(division = class1fGenerate, subject = fizyka, teacher = szott, numberOfActivities = 1)
        val curriculum1fGeografia = createCurriculum(division = class1fGenerate, subject = geografia, teacher = gonet, numberOfActivities = 2)
        val curriculum1fGodzWych = createCurriculum(division = class1fGenerate, subject = godzWych, teacher = chodorowiczBąk, numberOfActivities = 1)
        val curriculum1fHistoria = createCurriculum(division = class1fGenerate, subject = historia, teacher = dynowski, numberOfActivities = 3)
        val curriculum1fInformatyka = createCurriculum(division = class1fGenerate, subject = informatyka, teacher = dzierwa, numberOfActivities = 1)
        val curriculum1fJAngielski = createCurriculum(division = class1fGenerate, subject = jAngielski, teacher = trybusGorczyca, numberOfActivities = 3)
        val curriculum1fJdodatkowy = createCurriculum(division = class1fGenerate, subject = jNiemiecki, teacher = przybyłowiczCiszewska, numberOfActivities = 3)
        val curriculum1fJPolski = createCurriculum(division = class1fGenerate, subject = jPolski, teacher = grRe, numberOfActivities = 3)
        val curriculum1fMatematyka = createCurriculum(division = class1fGenerate, subject = matematyka, teacher = czuba, numberOfActivities = 3)
        val curriculum1fPodstawyPrzedsiębiorczości = createCurriculum(division = class1fGenerate, subject = podstawyPrzedsiębiorczości, teacher = suchodolski, numberOfActivities = 2)
        val curriculum1fReligia = createCurriculum(division = class1fGenerate, subject = religia, teacher = pPiernal, numberOfActivities = 2)
        val curriculum1fWiedzaOKulturze = createCurriculum(division = class1fGenerate, subject = wiedzaOKulturze, teacher = twardzikWilk, numberOfActivities = 1)
        val curriculum1fWiedzaOSpołeczeństwie = createCurriculum(division = class1fGenerate, subject = wiedzaOSpołeczeństwie, teacher = dynowski, numberOfActivities = 1)
        val curriculum1fWychowanieFizyczne = createCurriculum(division = class1fGenerate, subject = wychowanieFizyczne, teacher = dawidkoR, numberOfActivities = 2)

        curriculumListService.save(CurriculumListDTO(name = "test001", periodId = semestLetniPeriod.id, divisionOwnerId = lo2.id, curriculums = hashSetOf(
            curriculum1aBiologia,
            curriculum1aPodstawyPrzedsiębiorczości,
            curriculum1aJAngielski,
            curriculum1aReligia,
            curriculum1aFizyka,
            curriculum1aJNiemiecki,
            curriculum1aHistoria,
            curriculum1aJPolski,
            curriculum1aGeografia,
            curriculum1aMatematyka,
            curriculum1aChemia,
            curriculum1aWiedzaOSpołeczeństwie,
            curriculum1aWychowaniedoZyciaWRodzinie,
            curriculum1aWiedzaOKulturze,
            curriculum1aEdukacjaDoBezpieczeństwa,
            curriculum1aInformatyka,
            curriculum1aGodzWych,
            curriculum1aWychowanieFizyczne,

            curriculum1bPodstawyPrzedsiębiorczości,
            curriculum1bJPolski,
            curriculum1bEdukacjaDoBezpieczeństwa,
            curriculum1bJdodatkowy,
            curriculum1bGeografia,
            curriculum1bWiedzaOSpołeczeństwie,
            curriculum1bJAngielski,
            curriculum1bInformatyka,
            curriculum1bChemia,
            curriculum1bWychowanieFizyczne,
            curriculum1bMatematyka,
            curriculum1bWiedzaOKulturze,
            curriculum1bGodzWych,
            curriculum1bReligia,
            curriculum1bFizyka,
            curriculum1bWychowaniedoZyciaWRodzinie,
            curriculum1bBiologia,
            curriculum1bHistoria,

            curriculum1cBiologia,
                curriculum1cChemia,
                curriculum1cEdukacjaDoBezpieczeństwa,
                curriculum1cFizyka,
                curriculum1cGeografia,
                curriculum1cGodzWych,
                curriculum1cHistoria,
                curriculum1cInformatyka,
                curriculum1cJAngielski,
                curriculum1cJdodatkowy,
                curriculum1cJPolski,
                curriculum1cMatematyka,
                curriculum1cPodstawyPrzedsiębiorczości,
                curriculum1cReligia,
                curriculum1cWiedzaOKulturze,
                curriculum1cWiedzaOSpołeczeństwie,
                curriculum1cWychowaniedoZyciaWRodzinie,
                curriculum1cWychowanieFizyczne,

                curriculum1dBiologia,
                curriculum1dChemia,
                curriculum1dEdukacjaDoBezpieczeństwa,
                curriculum1dFizyka,
                curriculum1dGeografia,
                curriculum1dGodzWych,
                curriculum1dHistoria,
                curriculum1dInformatyka,
                curriculum1dJAngielski,
                curriculum1dJdodatkowy,
                curriculum1dJPolski,
                curriculum1dMatematyka,
                curriculum1dPodstawyPrzedsiębiorczości,
                curriculum1dReligia,
                curriculum1dWiedzaOKulturze,
                curriculum1dWiedzaOSpołeczeństwie,
                curriculum1dWychowanieFizyczne,

                curriculum1eBiologia,
                curriculum1eChemia,
                curriculum1eEdukacjaDoBezpieczeństwa,
                curriculum1eFizyka,
                curriculum1eGeografia,
                curriculum1eGodzWych,
                curriculum1eHistoria,
                curriculum1eInformatyka,
                curriculum1eJAngielski,
                curriculum1eJdodatkowy,
                curriculum1eJPolski,
                curriculum1eMatematyka,
                curriculum1ePodstawyPrzedsiębiorczości,
                curriculum1eReligia,
                curriculum1eWiedzaOKulturze,
                curriculum1eWiedzaOSpołeczeństwie,
                curriculum1eWychowanieFizyczne,

                curriculum1fBiologia,
                curriculum1fChemia,
                curriculum1fEdukacjaDoBezpieczeństwa,
                curriculum1fFizyka,
                curriculum1fGeografia,
                curriculum1fGodzWych,
                curriculum1fHistoria,
                curriculum1fInformatyka,
                curriculum1fJAngielski,
                curriculum1fJdodatkowy,
                curriculum1fJPolski,
                curriculum1fMatematyka,
                curriculum1fPodstawyPrzedsiębiorczości,
                curriculum1fReligia,
                curriculum1fWiedzaOKulturze,
                curriculum1fWiedzaOSpołeczeństwie,
                curriculum1fWychowanieFizyczne
        )))


        // =====================================================
        // Preference Teacher
        // =====================================================
        deptuch.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = -10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = 5),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        deptuch = teacherService.save(deptuch)

        janusz.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = 5),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        janusz = teacherService.save(janusz)

        stasik.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        stasik = teacherService.save(stasik)

        urbanek.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        urbanek = teacherService.save(urbanek)

        gierlach.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        gierlach = teacherService.save(gierlach)

        klein.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        klein = teacherService.save(klein)

        pernal.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))

            preferenceDataTimeForTeachers = hashSetOf(
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l0.id, dayOfWeek = 1, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 1, points = 10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l2.id, dayOfWeek = 1, points = 5),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l3.id, dayOfWeek = 1, points = 3),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l4.id, dayOfWeek = 1, points = 1),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l10.id, dayOfWeek = 1, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l11.id, dayOfWeek = 1, points = -10),

                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l0.id, dayOfWeek = 2, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 2, points = 10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l2.id, dayOfWeek = 2, points = 5),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l3.id, dayOfWeek = 2, points = 3),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l4.id, dayOfWeek = 2, points = 1),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l10.id, dayOfWeek = 2, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l11.id, dayOfWeek = 2, points = -10),

                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l0.id, dayOfWeek = 3, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 3, points = 10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l2.id, dayOfWeek = 3, points = 5),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l3.id, dayOfWeek = 3, points = 3),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l4.id, dayOfWeek = 3, points = 1),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l10.id, dayOfWeek = 3, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l11.id, dayOfWeek = 3, points = -10),

                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l0.id, dayOfWeek = 4, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 4, points = 10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l2.id, dayOfWeek = 4, points = 5),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l3.id, dayOfWeek = 4, points = 3),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l4.id, dayOfWeek = 4, points = 1),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l10.id, dayOfWeek = 4, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l11.id, dayOfWeek = 4, points = -10),

                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l0.id, dayOfWeek = 5, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 5, points = 10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l2.id, dayOfWeek = 5, points = 5),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l3.id, dayOfWeek = 5, points = 3),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l4.id, dayOfWeek = 5, points = 1),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l10.id, dayOfWeek = 5, points = -10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l11.id, dayOfWeek = 5, points = -10),

                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l0.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l2.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l3.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l4.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l5.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l6.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l7.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l8.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l9.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l10.id, dayOfWeek = 6, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l11.id, dayOfWeek = 6, points = -10000),

                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l0.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l2.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l3.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l4.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l5.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l6.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l7.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l8.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l9.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l10.id, dayOfWeek = 7, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l11.id, dayOfWeek = 7, points = -10000))
        }
        pernal = teacherService.save(pernal)

        grodeckaZaremba.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        grodeckaZaremba = teacherService.save(grodeckaZaremba)

        prajsnar.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        prajsnar = teacherService.save(prajsnar)

        dynowski.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        dynowski = teacherService.save(dynowski)

        longosz.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        longosz = teacherService.save(longosz)

        świstak.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        świstak = teacherService.save(świstak)

        baran.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        baran = teacherService.save(baran)

        karnas.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        karnas = teacherService.save(karnas)

        kasprzyk.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        kasprzyk = teacherService.save(kasprzyk)

        kijowska.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        kijowska = teacherService.save(kijowska)

        kolanko.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        kolanko = teacherService.save(kolanko)

        trybusGorczyca.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        trybusGorczyca = teacherService.save(trybusGorczyca)

        czuba.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
            preferenceDataTimeForTeachers = hashSetOf(
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 1, points = 10),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l1.id, dayOfWeek = 5, points = -10000),
                PreferenceDataTimeForTeacherDTO(teacherId = this.id, lessonId = l2.id, dayOfWeek = 1, points = 5))
        }
        czuba = teacherService.save(czuba)

        jastrzębska.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = 5),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        jastrzębska = teacherService.save(jastrzębska)

        mięsowicz.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        mięsowicz = teacherService.save(mięsowicz)

        hadel.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        hadel = teacherService.save(hadel)

        przybyłowicz.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        przybyłowicz = teacherService.save(przybyłowicz)

        przybyłowiczCiszewska.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        przybyłowiczCiszewska = teacherService.save(przybyłowiczCiszewska)

        rachwał.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        rachwał = teacherService.save(rachwał)

        chodorowiczBąk.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        chodorowiczBąk = teacherService.save(chodorowiczBąk)

        serwatka.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        serwatka = teacherService.save(serwatka)

        gonet.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        gonet = teacherService.save(gonet)

        guzik.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        guzik = teacherService.save(guzik)

        szarłowicz.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        szarłowicz = teacherService.save(szarłowicz)

        dawidkoJ.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        dawidkoJ = teacherService.save(dawidkoJ)

        wilk.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        wilk = teacherService.save(wilk)

        zając.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        zając = teacherService.save(zając)

        dawidkoR.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        dawidkoR = teacherService.save(dawidkoR)

        rachfał.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        rachfał = teacherService.save(rachfał)

        bloch.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = 5),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        bloch = teacherService.save(bloch)

        józefczyk.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = 5),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        józefczyk = teacherService.save(józefczyk)

        szott.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        szott = teacherService.save(szott)

        dzierwa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        dzierwa = teacherService.save(dzierwa)

        solecki.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = 5))
        }
        solecki = teacherService.save(solecki)

        kudroń.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        kudroń = teacherService.save(kudroń)

        pPiernal.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        pPiernal = teacherService.save(pPiernal)

        tPiwinski.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        tPiwinski = teacherService.save(tPiwinski)

        matwiej.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        matwiej = teacherService.save(matwiej)

        twardzikWilk.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        twardzikWilk = teacherService.save(twardzikWilk)

        suchodolski.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        suchodolski = teacherService.save(suchodolski)

        mSuchodolski.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        mSuchodolski = teacherService.save(mSuchodolski)

        łopuszańska.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
        }
        łopuszańska = teacherService.save(łopuszańska)


        // =====================================================
        // Preference Place
        // =====================================================
        p4.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = informatyka.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p5.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }

        p6.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p7.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p7g.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p8.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = biologia.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p10.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p13.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p14.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p15.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = fizyka.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p16.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p20.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = geografia.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p21.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p22.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = matematyka.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))

            preferenceTeacherByPlace = hashSetOf(PreferenceTeacherByPlaceDTO(placeId = this.id, teacherId = czuba.id, points = 5))

            preferenceDivisionByPlace = hashSetOf(PreferenceDivisionByPlaceDTO(placeId = this.id, divisionId = class1a.id, points = 5))
            placeService.save(this)
        }
        p24.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = chemia.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p25.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p31.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p35.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        p36.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }
        pds.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = godzWych.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
            placeService.save(this)
        }
        pS.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = godzWych.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
            placeService.save(this)
        }
        pb.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = godzWych.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
            placeService.save(this)
        }
        ph.apply {
            preferenceSubjectByPlace = hashSetOf(
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = 10),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = matematyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jPolski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jAngielski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jLaciński.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = godzWych.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = religia.id, points = -10_000),
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000))
            placeService.save(this)
        }
        pG4.apply {
            preferenceSubjectByPlace = hashSetOf(PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = wychowanieFizyczne.id, points = -10_000))
            placeService.save(this)
        }

        // =====================================================
        // Preference Divisions
        // =====================================================
        class1a.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLaciński.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = czuba.id, points = 5)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1a = divisionService.save(class1a)


        class1aGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLaciński.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = czuba.id, points = 5)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1aGenerate = divisionService.save(class1aGenerate)

        class1bGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLaciński.id, points = -10_000)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1bGenerate = divisionService.save(class1bGenerate)

        class1cGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLaciński.id, points = -10_000)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1cGenerate = divisionService.save(class1cGenerate)

        class1dGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLaciński.id, points = -10_000)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1dGenerate = divisionService.save(class1dGenerate)

        class1eGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLaciński.id, points = -10_000)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1eGenerate = divisionService.save(class1eGenerate)

        class1fGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLaciński.id, points = -10_000)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1fGenerate = divisionService.save(class1fGenerate)

    }

    private fun DivisionDTO.calculateTypicalPreferenceForTime(): HashSet<PreferenceDataTimeForDivisionDTO> {
        return hashSetOf(
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l0.id, dayOfWeek = 1, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l1.id, dayOfWeek = 1, points = 10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l2.id, dayOfWeek = 1, points = 5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l3.id, dayOfWeek = 1, points = 3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l4.id, dayOfWeek = 1, points = 1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l5.id, dayOfWeek = 1, points = -1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l6.id, dayOfWeek = 1, points = -2),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l7.id, dayOfWeek = 1, points = -2),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l8.id, dayOfWeek = 1, points = -3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l9.id, dayOfWeek = 1, points = -5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l10.id, dayOfWeek = 1, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l11.id, dayOfWeek = 1, points = -10),

            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l0.id, dayOfWeek = 2, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l1.id, dayOfWeek = 2, points = 10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l2.id, dayOfWeek = 2, points = 5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l3.id, dayOfWeek = 2, points = 3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l4.id, dayOfWeek = 2, points = 1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l5.id, dayOfWeek = 2, points = 0),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l6.id, dayOfWeek = 2, points = -1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l7.id, dayOfWeek = 2, points = -2),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l8.id, dayOfWeek = 2, points = -3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l9.id, dayOfWeek = 2, points = -5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l10.id, dayOfWeek = 2, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l11.id, dayOfWeek = 2, points = -10),

            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l0.id, dayOfWeek = 3, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l1.id, dayOfWeek = 3, points = 10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l2.id, dayOfWeek = 3, points = 5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l3.id, dayOfWeek = 3, points = 3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l4.id, dayOfWeek = 3, points = 1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l5.id, dayOfWeek = 3, points = 0),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l6.id, dayOfWeek = 3, points = -1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l7.id, dayOfWeek = 3, points = -2),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l8.id, dayOfWeek = 3, points = -3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l9.id, dayOfWeek = 3, points = -5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l10.id, dayOfWeek = 3, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l11.id, dayOfWeek = 3, points = -10),

            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l0.id, dayOfWeek = 4, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l1.id, dayOfWeek = 4, points = 10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l2.id, dayOfWeek = 4, points = 5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l3.id, dayOfWeek = 4, points = 3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l4.id, dayOfWeek = 4, points = 1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l5.id, dayOfWeek = 4, points = 0),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l6.id, dayOfWeek = 4, points = -1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l7.id, dayOfWeek = 4, points = -2),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l8.id, dayOfWeek = 4, points = -3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l9.id, dayOfWeek = 4, points = -5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l10.id, dayOfWeek = 4, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l11.id, dayOfWeek = 4, points = -10),

            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l0.id, dayOfWeek = 5, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l1.id, dayOfWeek = 5, points = 10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l2.id, dayOfWeek = 5, points = 5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l3.id, dayOfWeek = 5, points = 3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l4.id, dayOfWeek = 5, points = 1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l5.id, dayOfWeek = 5, points = -1),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l6.id, dayOfWeek = 5, points = -2),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l7.id, dayOfWeek = 5, points = -2),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l8.id, dayOfWeek = 5, points = -3),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l9.id, dayOfWeek = 5, points = -5),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l10.id, dayOfWeek = 5, points = -10),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l11.id, dayOfWeek = 5, points = -10),

            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l0.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l1.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l2.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l3.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l4.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l5.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l6.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l7.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l8.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l9.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l10.id, dayOfWeek = 6, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l11.id, dayOfWeek = 6, points = -10000),

            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l0.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l1.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l2.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l3.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l4.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l5.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l6.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l7.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l8.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l9.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l10.id, dayOfWeek = 7, points = -10000),
            PreferenceDataTimeForDivisionDTO(divisionId = this.id, lessonId = l11.id, dayOfWeek = 7, points = -10000))
    }


    private fun createPlace(name: String, shortName: String? = null, numberOfSeats: Long?, division: DivisionDTO): PlaceDTO {
        val place = PlaceDTO(name = name, shortName = shortName, numberOfSeats = numberOfSeats, divisionOwnerId = division.id)
        return placeService.save(place)
    }

    private fun createSubject(name: String, shortName: String, divisionOwner: DivisionDTO): SubjectDTO {
        val subject = SubjectDTO(name = name, shortName = shortName, divisionOwnerId = divisionOwner.id)
        return subjectService.save(subject)
    }

    private fun createTeacher(degree: String, name: String, surname: String, shortName: String? = null, divisionOwner: DivisionDTO): TeacherDTO {
        val teacher = TeacherDTO(degree = degree, name = name, surname = surname, divisionOwnerId = divisionOwner.id)
        return teacherService.save(teacher)
    }

    private fun createLesson(name: String, startTime: String, endTime: String, divisionOwner: DivisionDTO): LessonDTO {
        val lesson = LessonDTO(name = name, divisionOwnerId = divisionOwner.id, startTimeString = startTime, endTimeString = endTime)
        return lessonService.save(lesson)
    }

    private fun createDivision(name: String, shortName: String, divisionType: DivisionType, numberOfPeople: Long, parents: Set<DivisionDTO> = emptySet()): DivisionDTO {
        val division = DivisionDTO(name = name, shortName = shortName, divisionType = divisionType, numberOfPeople = numberOfPeople, parents = parents)
        return divisionService.save(division)
    }

    private fun createCurriculum(subject: SubjectDTO, teacher: TeacherDTO, division: DivisionDTO, numberOfActivities: Long, place: PlaceDTO? = null): CurriculumDTO {
        val curriculum = CurriculumDTO(subjectId = subject.id, teacherId = teacher.id, divisionId = division.id, placeId = place?.id, numberOfActivities = numberOfActivities)
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
