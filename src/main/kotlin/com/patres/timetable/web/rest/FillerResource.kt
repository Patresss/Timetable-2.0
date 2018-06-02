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
    private var placeService: PlaceService,
    private var userService: UserService) {

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

    var div1aWychowanieFizyczne = DivisionDTO()
    var div1aGirlWychowanieFizyczne = DivisionDTO()
    var div1aBoyWychowanieFizyczne = DivisionDTO()

    var div1aGrupy = DivisionDTO()
    var div1aGrupa1 = DivisionDTO()
    var div1aGrupa2 = DivisionDTO()

    var div1Gr1JezykObcy = DivisionDTO()
    var div1Gr1Niemiecki1 = DivisionDTO()
    var div1Gr1Niemiecki2 = DivisionDTO()
    var div1Gr1Niemiecki3 = DivisionDTO()
    var div1Gr1Rosyjski1 = DivisionDTO()
    var div1Gr1Rosyjski2 = DivisionDTO()
    var div1Gr1Francuski1 = DivisionDTO()



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
    var jRosyjski = SubjectDTO()
    var godzWych = SubjectDTO()
    var edukacjaDoBezpieczeństwa = SubjectDTO()
    var wychowanieFizyczne = SubjectDTO()
    var religia = SubjectDTO()
    var wychowaniedoZyciaWRodzinie = SubjectDTO()
    var jLacińskiPrawniczy = SubjectDTO()
    var techAngielski = SubjectDTO()
    var historiaISpoleczenstwo = SubjectDTO()
    var ekonomiaWPraktyce = SubjectDTO()
    var przyroda = SubjectDTO()
    var literaturaObca = SubjectDTO()
    var edukacjaDziennikarska = SubjectDTO()
    var medycznyJezykAngielski = SubjectDTO()
    var fizykaMedyczna = SubjectDTO()


    // =====================================================
    // Teacher
    // =====================================================
    var DeWi = TeacherDTO()
    var JaEd = TeacherDTO()
    var StBo = TeacherDTO()
    var urbanek = TeacherDTO()
    var gierlach = TeacherDTO()
    var KlLu = TeacherDTO()
    var PeRe = TeacherDTO()
    var GrRe = TeacherDTO()
    var PrAr = TeacherDTO()
    var DyLu = TeacherDTO()
    var LoEl = TeacherDTO()
    var SwEl = TeacherDTO()
    var BaEl = TeacherDTO()
    var KaMo = TeacherDTO()
    var KaTo = TeacherDTO()
    var KiBe = TeacherDTO()
    var KoIr = TeacherDTO()
    var TrAg = TeacherDTO()
    var CzBe = TeacherDTO()
    var JaMa = TeacherDTO()
    var MiJo = TeacherDTO()
    var HaAn = TeacherDTO()
    var PrAn = TeacherDTO()
    var PrKa = TeacherDTO()
    var RaBe = TeacherDTO()
    var ChAg = TeacherDTO()
    var SeMo = TeacherDTO()
    var GoTa = TeacherDTO()
    var GuMa = TeacherDTO()
    var SzTo = TeacherDTO()
    var DaJa = TeacherDTO()
    var WiWo = TeacherDTO()
    var ZaTo = TeacherDTO()
    var DaRe = TeacherDTO()
    var RaMa = TeacherDTO()
    var BlSł = TeacherDTO()
    var JóSt = TeacherDTO()
    var SzIr = TeacherDTO()
    var DzMa = TeacherDTO()
    var SoRy = TeacherDTO()
    var KuKa = TeacherDTO()
    var PePi = TeacherDTO()
    var PiTa = TeacherDTO()
    var matwiej = TeacherDTO()
    var TwMa = TeacherDTO()
    var SuZb = TeacherDTO()
    var SM = TeacherDTO()
    var ŁoDo = TeacherDTO()
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
        jLacińskiPrawniczy = createSubject(name = "J. łaciński prawniczy", shortName = "ŁAC", divisionOwner = lo2)
        jRosyjski = createSubject(name = "J. rosyjski", shortName = "ROS", divisionOwner = lo2)
        godzWych = createSubject(name = "Godzina Wychowawcza", shortName = "GW", divisionOwner = lo2)
        edukacjaDoBezpieczeństwa = createSubject(name = "Edukacja do bezpieczeństwa", shortName = "EDB", divisionOwner = lo2)
        wychowanieFizyczne = createSubject(name = "Wychowanie Fizyczne", shortName = "WF", divisionOwner = lo2)
        religia = createSubject(name = "Religia", shortName = "REL", divisionOwner = lo2)
        wychowaniedoZyciaWRodzinie = createSubject(name = "Wychowanie do życia w rodzinie", shortName = "WDŻWR", divisionOwner = lo2)

        techAngielski = createSubject(name = "Techniczny angielski", shortName = "TANG", divisionOwner = lo2)
        historiaISpoleczenstwo = createSubject(name = "Historia i Społeczeństwo", shortName = "HISP", divisionOwner = lo2)
        ekonomiaWPraktyce = createSubject(name = "Ekonomia w praktyce", shortName = "EKON", divisionOwner = lo2)
        przyroda = createSubject(name = "Przyroda", shortName = "PRZY", divisionOwner = lo2)
        edukacjaDziennikarska = createSubject(name = "Edukacja dziennikarska", shortName = "EDZ", divisionOwner = lo2)
        literaturaObca = createSubject(name = "Literatura obca", shortName = "LIO", divisionOwner = lo2)
        fizykaMedyczna = createSubject(name = "Fizyka medyczna", shortName = "MFIZ", divisionOwner = lo2)
        medycznyJezykAngielski = createSubject(name = "Medyczny język angielski", shortName = "MJA", divisionOwner = lo2)

        // =====================================================
        // Teacher
        // =====================================================
        DeWi = createTeacher(degree = "mgr", name = "Witold", surname = "Deptuch", divisionOwner = lo2)
        JaEd = createTeacher(degree = "mgr", name = "Edyta", surname = "Janusz", divisionOwner = lo2)
        StBo = createTeacher(degree = "mgr", name = "Bogusława", surname = "Stasik", divisionOwner = lo2)
        urbanek = createTeacher(degree = "mgr", name = "Jadwiga", surname = "Urbanek", divisionOwner = lo2)
        gierlach = createTeacher(degree = "mgr", name = "Anna", surname = "Gierlach", divisionOwner = lo2)
        KlLu = createTeacher(degree = "mgr", name = "Lucyna", surname = "Klein", divisionOwner = lo2)
        PeRe = createTeacher(degree = "mgr", name = "Renata", surname = "Pernal", divisionOwner = lo2)
        GrRe = createTeacher(degree = "mgr", name = "Tamara", surname = "Grodecka-Zaremba", divisionOwner = lo2)
        PrAr = createTeacher(degree = "mgr", name = "Arkadiusz", surname = "Prajsnar", divisionOwner = lo2)
        DyLu = createTeacher(degree = "mgr", name = "Lucjan", surname = "Dynowski", divisionOwner = lo2)
        LoEl = createTeacher(degree = "dr", name = "Elżbieta", surname = "Longosz", divisionOwner = lo2)
        SwEl = createTeacher(degree = "mgr", name = "Elżbieta", surname = "Swistak", divisionOwner = lo2)
        BaEl = createTeacher(degree = "mgr", name = "Elżbieta", surname = "Baran", divisionOwner = lo2)
        KaMo = createTeacher(degree = "mgr", name = "Monika", surname = "Karnas", divisionOwner = lo2)
        KaTo = createTeacher(degree = "mgr", name = "Tomasz", surname = "Kasprzyk", divisionOwner = lo2)
        KiBe = createTeacher(degree = "mgr", name = "Beata", surname = "Kijowska", divisionOwner = lo2)
        KoIr = createTeacher(degree = "mgr", name = "Irena", surname = "Kolanko", divisionOwner = lo2)
        TrAg = createTeacher(degree = "mgr", name = "Agnieszka", surname = "Trybus-Gorczyca", divisionOwner = lo2)
        CzBe = createTeacher(degree = "mgr", name = "Beata", surname = "Czuba", divisionOwner = lo2)
        JaMa = createTeacher(degree = "mgr", name = "Mariola", surname = "Jastrzębska", divisionOwner = lo2)
        MiJo = createTeacher(degree = "mgr", name = "Jolanta", surname = "Mięsowicz", divisionOwner = lo2)
        HaAn = createTeacher(degree = "mgr", name = "Anna", surname = "Hadel", divisionOwner = lo2)
        PrAn = createTeacher(degree = "mgr", name = "Anna", surname = "Przybyłowicz", divisionOwner = lo2)
        PrKa = createTeacher(degree = "mgr", name = "Katarzyna", surname = "Przybyłowicz-Ciszewska", divisionOwner = lo2)
        RaBe = createTeacher(degree = "mgr", name = "Beata", surname = "Rachwał", divisionOwner = lo2)
        ChAg = createTeacher(degree = "dr", name = "Agata", surname = "Chodorowicz-Bąk", divisionOwner = lo2)
        SeMo = createTeacher(degree = "mgr", name = "Monika", surname = "Serwatka", divisionOwner = lo2)
        GoTa = createTeacher(degree = "mgr", name = "Tatiana", surname = "Gonet", divisionOwner = lo2)
        GuMa = createTeacher(degree = "mgr", name = "Maciej", surname = "Guzik", divisionOwner = lo2)
        SzTo = createTeacher(degree = "mgr", name = "Tomasz", surname = "Szarłowicz", divisionOwner = lo2)
        DaJa = createTeacher(degree = "mgr", name = "Jacek", surname = "Dawidko", divisionOwner = lo2)
        WiWo = createTeacher(degree = "mgr", name = "Wojciech", surname = "Wilk", divisionOwner = lo2)
        ZaTo = createTeacher(degree = "mgr", name = "Tomasz", surname = "Zając", divisionOwner = lo2)
        DaRe = createTeacher(degree = "mgr", name = "Renata", surname = "Dawidko", divisionOwner = lo2)
        RaMa = createTeacher(degree = "mgr", name = "Maria", surname = "Rachfał", divisionOwner = lo2)
        BlSł = createTeacher(degree = "mgr inż	", name = "Sławomir", surname = "Bloch", divisionOwner = lo2)
        JóSt = createTeacher(degree = "mgr", name = "Stanisław", surname = "Józefczyk", divisionOwner = lo2)
        SzIr = createTeacher(degree = "mgr", name = "Irma", surname = "Szott", divisionOwner = lo2)
        DzMa = createTeacher(degree = "mgr", name = "Małgorzata", surname = "Dzierwa", divisionOwner = lo2)
        SoRy = createTeacher(degree = "mgr", name = "Ryszard", surname = "SoRy", divisionOwner = lo2)
        KuKa = createTeacher(degree = "mgr", name = "Katarzyna", surname = "Kudroń", divisionOwner = lo2)
        PePi = createTeacher(degree = "ks	 mgr", name = "Piotr", surname = "Pernal", divisionOwner = lo2)
        PiTa = createTeacher(degree = "ks	 mgr", name = "Tadeusz", surname = "Piwiński", divisionOwner = lo2)
        matwiej = createTeacher(degree = "mgr", name = "Wojciech", surname = "Matwiej", divisionOwner = lo2)
        TwMa = createTeacher(degree = "mgr", name = "Małgorzata", surname = "Twardzik-Wilk", divisionOwner = lo2)
        SuZb = createTeacher(degree = "mgr inż	", name = "Zbigniew", surname = "Suchodolski", divisionOwner = lo2)
        SM = createTeacher(degree = "mgr", name = "Mateusz", surname = "Suchodolski", divisionOwner = lo2)
        ŁoDo = createTeacher(degree = "mgr", name = "Dorota ", surname = "Łopuszańska-Patrylak", divisionOwner = lo2)

        // =====================================================
        // Division
        // =====================================================
        class1a = createDivision(name = "1 A", shortName = "1 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1b = createDivision(name = "1 B", shortName = "1 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1c = createDivision(name = "1 C", shortName = "1 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1d = createDivision(name = "1 D", shortName = "1 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1e = createDivision(name = "1 E", shortName = "1 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1f = createDivision(name = "1 F", shortName = "1 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2a = createDivision(name = "2 A", shortName = "1 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2b = createDivision(name = "2 B", shortName = "2 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2c = createDivision(name = "2 C", shortName = "2 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2d = createDivision(name = "2 D", shortName = "2 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2e = createDivision(name = "2 E", shortName = "2 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2f = createDivision(name = "2 F", shortName = "2 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3a = createDivision(name = "3 A", shortName = "3 A", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3b = createDivision(name = "3 B", shortName = "3 B", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3c = createDivision(name = "3 C", shortName = "3 C", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3d = createDivision(name = "3 D", shortName = "3 D", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3e = createDivision(name = "3 E", shortName = "3 E", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3f = createDivision(name = "3 F", shortName = "3 F", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        div1aG1 = createDivision(name = "1A gr	 1", shortName = "Ang 1A gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1a))
        div1aG2 = createDivision(name = "1A gr	 2", shortName = "Ang 1A gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1a))
        divNie1G1 = createDivision(name = "Niemiecki 1 gr	 1", shortName = "Niem 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1a))
        divNie1G2 = createDivision(name = "Niemiecki 1 gr	 2", shortName = "Niem 1 gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1b))
        divFra1G1 = createDivision(name = "Francuski 1 gr	 1", shortName = "Fra 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1a))
        divRos1G1 = createDivision(name = "Rosyjski 1 gr	 1", shortName = "Ros 1 gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1a))
        divRos1G2 = createDivision(name = "Rosyjski 1 gr	 2", shortName = "Ros 1 gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1a))
        div1GrCh1 = createDivision(name = "WF 1 gr	 Chłopcy 1", shortName = "WF 1 gr	 Ch 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1a))
        div1GrCh2 = createDivision(name = "WF 1 gr	 Chłopcy 2", shortName = "WF 1 gr	 Ch 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1b, class1c))
        div1GrDz1 = createDivision(name = "WF 1 gr	 Dziewczyny 1", shortName = "WF 1 gr	 Dz 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1a, class1c))
        div1GrDz2 = createDivision(name = "WF 1 gr	 Dziewczyny 2", shortName = "WF 1 gr	 Dz 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1c))
        div1bG1 = createDivision(name = "1B gr	 1", shortName = "Ang 1B gr	 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1b))
        div1bG2 = createDivision(name = "1B gr	 2", shortName = "Ang 1B gr	 2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1b))
        div1Gr1bDz1 = createDivision(name = "WF 1 gr	 Dziewczyny 1", shortName = "WF 1 gr	 Dz 1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(class1b))


        class1aGenerate = createDivision(name = "1 A generate", shortName = "1 A G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1bGenerate = createDivision(name = "1 B generate", shortName = "1 B G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1cGenerate = createDivision(name = "1 C generate", shortName = "1 C G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1dGenerate = createDivision(name = "1 D generate", shortName = "1 D G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1eGenerate = createDivision(name = "1 E generate", shortName = "1 E G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class1fGenerate = createDivision(name = "1 F generate", shortName = "1 F G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)

        class2aGenerate = createDivision(name = "2 A generate", shortName = "2 A G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2bGenerate = createDivision(name = "2 B generate", shortName = "2 B G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2cGenerate = createDivision(name = "2 C generate", shortName = "2 C G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2dGenerate = createDivision(name = "2 D generate", shortName = "2 D G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2eGenerate = createDivision(name = "2 E generate", shortName = "2 E G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class2fGenerate = createDivision(name = "2 F generate", shortName = "2 F G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)

        class3aGenerate = createDivision(name = "3 A generate", shortName = "3 A G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3bGenerate = createDivision(name = "3 B generate", shortName = "3 B G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3cGenerate = createDivision(name = "3 C generate", shortName = "3 C G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3dGenerate = createDivision(name = "3 D generate", shortName = "3 D G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3eGenerate = createDivision(name = "3 E generate", shortName = "3 E G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)
        class3fGenerate = createDivision(name = "3 F generate", shortName = "3 F G", divisionType = DivisionType.CLASS, numberOfPeople = 32, schoolId  = lo2.id)


        div1aWychowanieFizyczne = createDivision(name = "div1aWychowanieFizyczne", shortName = "div1aWychowanieFizyczne", divisionType = DivisionType.SET_OF_SUBGROUPS, numberOfPeople = 32, schoolId  = lo2.id, parents = hashSetOf(class1aGenerate))
        div1aGirlWychowanieFizyczne = createDivision(name = "div1aGirlWychowanieFizyczne", shortName = "div1aGirlWychowanieFizyczne", divisionType = DivisionType.SUBGROUP, numberOfPeople = 15, schoolId  = lo2.id, parents = hashSetOf(div1aWychowanieFizyczne, class1aGenerate))
        div1aBoyWychowanieFizyczne = createDivision(name = "div1aBoyWychowanieFizyczne", shortName = "div1aBoyWychowanieFizyczne", divisionType = DivisionType.SUBGROUP, numberOfPeople = 15, schoolId  = lo2.id, parents = hashSetOf(div1aWychowanieFizyczne, class1aGenerate))


        div1aGrupy = createDivision(name = "div1aGrupy", shortName = "div1aGrupy", divisionType = DivisionType.SET_OF_SUBGROUPS, numberOfPeople = 32, schoolId  = lo2.id, parents = hashSetOf(class1aGenerate))
        div1aGrupa1 = createDivision(name = "div1aGrupa1", shortName = "div1aGrupa1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(div1aGrupy, class1aGenerate))
        div1aGrupa2 = createDivision(name = "div1aGrupa2", shortName = "div1aGrupa2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(div1aGrupy, class1aGenerate))

        div1Gr1JezykObcy = createDivision(name = "div1Gr1JezykObcy", shortName = "div1Gr1JezykObcy", divisionType = DivisionType.SET_OF_SUBGROUPS, numberOfPeople = 32, schoolId  = lo2.id, parents = hashSetOf(class1aGenerate))
        div1Gr1Niemiecki1 = createDivision(name = " div1Gr1Niemiecki1", shortName = "div1Gr1Niemiecki1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(div1Gr1JezykObcy, class1aGenerate))
        div1Gr1Niemiecki2 = createDivision(name = " div1Gr1Niemiecki2", shortName = "div1Gr1Niemiecki2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(div1Gr1JezykObcy, class1bGenerate))
        div1Gr1Niemiecki3 = createDivision(name = " div1Gr1Niemiecki3", shortName = "div1Gr1Niemiecki3", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(div1Gr1JezykObcy, class1cGenerate))
        div1Gr1Rosyjski1 = createDivision(name = " div1Gr1Rosyjski1", shortName = "div1Gr1Rosyjski1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(div1Gr1JezykObcy, class1aGenerate))
        div1Gr1Rosyjski2 = createDivision(name = " div1Gr1Rosyjski2", shortName = "div1Gr1Rosyjski2", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(div1Gr1JezykObcy, class1bGenerate, class1cGenerate))
        div1Gr1Francuski1 = createDivision(name = " div1Gr1Francuski1", shortName = "div1Gr1Francuski1", divisionType = DivisionType.SUBGROUP, numberOfPeople = 16, schoolId  = lo2.id, parents = hashSetOf(div1Gr1JezykObcy, class1aGenerate, class1bGenerate, class1cGenerate))


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

        d1aMon2 = createTimetable(dayOfWeek = 1, lesson = l2, subject = biologia, teacher = SzTo, place = p8, division = class1a, period = semestLetniPeriod)
        d1aMon3 = createTimetable(dayOfWeek = 1, lesson = l3, subject = podstawyPrzedsiębiorczości, teacher = SuZb, place = p35, division = class1a, period = semestLetniPeriod)
        d1aMon4a = createTimetable(dayOfWeek = 1, lesson = l4, subject = jAngielski, teacher = KiBe, place = p7g, division = div1aG1, period = semestLetniPeriod)
        d1aMon4b = createTimetable(dayOfWeek = 1, lesson = l4, subject = jAngielski, teacher = KaTo, place = p21, division = div1aG2, period = semestLetniPeriod)
        d1aMon5 = createTimetable(dayOfWeek = 1, lesson = l5, subject = religia, teacher = PiTa, place = p36, division = class1a, period = semestLetniPeriod)
        d1aMon6 = createTimetable(dayOfWeek = 1, lesson = l6, subject = fizyka, teacher = SzIr, place = p15, division = class1a, period = semestLetniPeriod)
        d1aMon8 = createTimetable(dayOfWeek = 1, lesson = l8, subject = historia, teacher = SwEl, place = p7, division = class1a, period = semestLetniPeriod)
        d1aMon9Test = createTimetable(dayOfWeek = 1, lesson = l9, subject = historia, teacher = SwEl, place = p7, division = class1a, period = semestLetniPeriod, everyWeek = 2)

        d1aTue1 = createTimetable(dayOfWeek = 2, lesson = l1, subject = jPolski, teacher = PeRe, place = p24, division = class1a, period = semestLetniPeriod)
        d1aTue2 = createTimetable(dayOfWeek = 2, lesson = l2, subject = geografia, teacher = GoTa, place = p20, division = class1a, period = semestLetniPeriod)
        d1aTue3a = createTimetable(dayOfWeek = 2, lesson = l3, subject = jAngielski, teacher = KiBe, place = p4, division = div1aG1, period = semestLetniPeriod)
        d1aTue3b = createTimetable(dayOfWeek = 2, lesson = l3, subject = jAngielski, teacher = KaTo, place = p7g, division = div1aG2, period = semestLetniPeriod)
        d1aTue4 = createTimetable(dayOfWeek = 2, lesson = l4, subject = matematyka, teacher = CzBe, place = p22, division = class1a, period = semestLetniPeriod)
        d1aTue5 = createTimetable(dayOfWeek = 2, lesson = l5, subject = matematyka, teacher = CzBe, place = p22, division = class1a, period = semestLetniPeriod)
        d1aTue6 = createTimetable(dayOfWeek = 2, lesson = l6, subject = chemia, teacher = ChAg, place = p24, division = class1a, period = semestLetniPeriod)
        d1aTue7 = createTimetable(dayOfWeek = 2, lesson = l7, subject = fizyka, teacher = SzIr, place = p15, division = class1a, period = semestLetniPeriod)

        d1aWen1 = createTimetable(dayOfWeek = 3, lesson = l1, subject = wiedzaOSpołeczeństwie, teacher = SwEl, place = p24, division = class1a, period = semestLetniPeriod)
        d1aWen3 = createTimetable(dayOfWeek = 3, lesson = l3, subject = historia, teacher = SwEl, place = p24, division = class1a, period = semestLetniPeriod)
        d1aWen4 = createTimetable(dayOfWeek = 3, lesson = l4, subject = wychowaniedoZyciaWRodzinie, teacher = SoRy, place = p35, division = class1a, period = semestLetniPeriod)

        d1aThu1 = createTimetable(dayOfWeek = 4, lesson = l1, subject = podstawyPrzedsiębiorczości, teacher = SM, place = p36, division = class1a, period = semestLetniPeriod)
        d1aThu2 = createTimetable(dayOfWeek = 4, lesson = l2, subject = matematyka, teacher = CzBe, place = p22, division = class1a, period = semestLetniPeriod)
        d1aThu3 = createTimetable(dayOfWeek = 4, lesson = l3, subject = wiedzaOKulturze, teacher = TwMa, place = p13, division = class1a, period = semestLetniPeriod)
        d1aThu4 = createTimetable(dayOfWeek = 4, lesson = l4, subject = edukacjaDoBezpieczeństwa, teacher = BlSł, place = p31, division = class1a, period = semestLetniPeriod)
        d1aThu5a = createTimetable(dayOfWeek = 4, lesson = l5, subject = jAngielski, teacher = KiBe, place = p14, division = div1aG1, period = semestLetniPeriod)
        d1aThu5b = createTimetable(dayOfWeek = 4, lesson = l5, subject = informatyka, teacher = DzMa, place = p4, division = div1aG2, period = semestLetniPeriod)
        d1aThu6a = createTimetable(dayOfWeek = 4, lesson = l6, subject = informatyka, teacher = DzMa, place = p4, division = div1aG1, period = semestLetniPeriod)
        d1aThu6b = createTimetable(dayOfWeek = 4, lesson = l6, subject = jAngielski, teacher = KaTo, place = p5, division = div1aG2, period = semestLetniPeriod)

        d1aThu7 = createTimetable(dayOfWeek = 4, lesson = l7, subject = godzWych, teacher = CzBe, place = p22, division = class1a, period = semestLetniPeriod)

        d1aFri4 = createTimetable(dayOfWeek = 5, lesson = l4, subject = jPolski, teacher = PeRe, place = p16, division = class1a, period = semestLetniPeriod)
        d1aFri5 = createTimetable(dayOfWeek = 5, lesson = l5, subject = jPolski, teacher = PeRe, place = p16, division = class1a, period = semestLetniPeriod)
        d1aFri6 = createTimetable(dayOfWeek = 5, lesson = l6, subject = matematyka, teacher = CzBe, place = p36, division = class1a, period = semestLetniPeriod)
        d1aFri8 = createTimetable(dayOfWeek = 5, lesson = l8, subject = religia, teacher = PiTa, place = p22, division = class1a, period = semestLetniPeriod)

        d1aMon7a = createTimetable(dayOfWeek = 1, lesson = l7, subject = jNiemiecki, teacher = HaAn, place = p14, division = divNie1G2, period = semestLetniPeriod)
        d1aMon7b = createTimetable(dayOfWeek = 1, lesson = l7, subject = jFrancuski, teacher = RaBe, place = p25, division = divFra1G1, period = semestLetniPeriod)
        d1aMon7c = createTimetable(dayOfWeek = 1, lesson = l7, subject = jRosyjski, teacher = ŁoDo, place = p6, division = divRos1G1, period = semestLetniPeriod)
        d1aMon7d = createTimetable(dayOfWeek = 1, lesson = l7, subject = jRosyjski, teacher = StBo, place = p10, division = divRos1G2, period = semestLetniPeriod)

        d1aWen2a = createTimetable(dayOfWeek = 3, lesson = l2, subject = jNiemiecki, teacher = HaAn, place = p14, division = divNie1G2, period = semestLetniPeriod)
        d1vWen2b = createTimetable(dayOfWeek = 3, lesson = l2, subject = jFrancuski, teacher = RaBe, place = p13, division = divFra1G1, period = semestLetniPeriod)
        d1aWen2c = createTimetable(dayOfWeek = 3, lesson = l2, subject = jRosyjski, teacher = ŁoDo, place = pG4, division = divRos1G1, period = semestLetniPeriod)
        d1aWen2d = createTimetable(dayOfWeek = 3, lesson = l2, subject = jRosyjski, teacher = StBo, place = p12, division = divRos1G2, period = semestLetniPeriod)

        d1aFri7a = createTimetable(dayOfWeek = 5, lesson = l7, subject = jNiemiecki, teacher = HaAn, place = p14, division = divNie1G2, period = semestLetniPeriod)
        d1vFri7b = createTimetable(dayOfWeek = 5, lesson = l7, subject = jFrancuski, teacher = RaBe, place = p24, division = divFra1G1, period = semestLetniPeriod)
        d1aFri7c = createTimetable(dayOfWeek = 5, lesson = l7, subject = jRosyjski, teacher = ŁoDo, place = p6, division = divRos1G1, period = semestLetniPeriod)
        d1aFri7d = createTimetable(dayOfWeek = 5, lesson = l7, subject = jRosyjski, teacher = StBo, place = p13, division = divRos1G2, period = semestLetniPeriod)

        d1WfGrDz1Fri2 = createTimetable(dayOfWeek = 5, lesson = l2, subject = wychowanieFizyczne, teacher = WiWo, place = ph, division = div1GrDz1, period = semestLetniPeriod)
        d1WfGrCh1Fri2 = createTimetable(dayOfWeek = 5, lesson = l2, subject = wychowanieFizyczne, teacher = ZaTo, place = ph, division = div1GrCh1, period = semestLetniPeriod)
        d1WfGrCh2Fri2 = createTimetable(dayOfWeek = 5, lesson = l2, subject = wychowanieFizyczne, teacher = DaJa, place = ph, division = div1GrCh2, period = semestLetniPeriod)

        d1WfGrDz1Fri3 = createTimetable(dayOfWeek = 5, lesson = l3, subject = wychowanieFizyczne, teacher = WiWo, place = ph, division = div1GrDz1, period = semestLetniPeriod)
        d1WfGrDz2Fri3 = createTimetable(dayOfWeek = 5, lesson = l3, subject = wychowanieFizyczne, teacher = RaMa, place = pS, division = div1GrDz2, period = semestLetniPeriod)
        d1WfGrCh1Fri3 = createTimetable(dayOfWeek = 5, lesson = l3, subject = wychowanieFizyczne, teacher = ZaTo, place = ph, division = div1GrCh1, period = semestLetniPeriod)
        d1WfGrCh2Fri3 = createTimetable(dayOfWeek = 5, lesson = l3, subject = wychowanieFizyczne, teacher = DaJa, place = ph, division = div1GrCh2, period = semestLetniPeriod)

        // Calass 1B
        d1bMon2 = createTimetable(dayOfWeek = 1, lesson = l2, subject = podstawyPrzedsiębiorczości, teacher = SM, place = p10, division = class1b, period = semestLetniPeriod)
        d1bMon3 = createTimetable(dayOfWeek = 1, lesson = l3, subject = jPolski, teacher = KlLu, place = p13, division = class1b, period = semestLetniPeriod)
        d1bMon4 = createTimetable(dayOfWeek = 1, lesson = l4, subject = jPolski, teacher = KlLu, place = p13, division = class1b, period = semestLetniPeriod)
        d1bMon5 = createTimetable(dayOfWeek = 1, lesson = l5, subject = edukacjaDoBezpieczeństwa, teacher = BlSł, place = p31, division = class1b, period = semestLetniPeriod)
        d1bMon6 = createTimetable(dayOfWeek = 1, lesson = l6, subject = historia, teacher = SwEl, place = p35, division = class1b, period = semestLetniPeriod)
        d1bMon7a = createTimetable(dayOfWeek = 1, lesson = l7, subject = jNiemiecki, teacher = PrKa, place = p13, division = divNie1G1, period = semestLetniPeriod)
        d1bMon8 = createTimetable(dayOfWeek = 1, lesson = l8, subject = geografia, teacher = GoTa, place = p20, division = class1b, period = semestLetniPeriod)

        d1bTue5 = createTimetable(dayOfWeek = 2, lesson = l5, subject = wiedzaOSpołeczeństwie, teacher = DyLu, place = p7, division = class1b, period = semestLetniPeriod)
        d1aTue6a = createTimetable(dayOfWeek = 2, lesson = l6, subject = jAngielski, teacher = KaMo, place = p35, division = div1bG1, period = semestLetniPeriod)
        d1aTue6b = createTimetable(dayOfWeek = 2, lesson = l6, subject = jAngielski, teacher = KiBe, place = p6, division = div1bG2, period = semestLetniPeriod)
        d1bTue7 = createTimetable(dayOfWeek = 2, lesson = l7, subject = biologia, teacher = SzTo, place = p8, division = class1b, period = semestLetniPeriod)
        d1bTue8 = createTimetable(dayOfWeek = 2, lesson = l8, subject = chemia, teacher = ChAg, place = p24, division = class1b, period = semestLetniPeriod)
        d1bTue9a = createTimetable(dayOfWeek = 2, lesson = l9, subject = wychowanieFizyczne, teacher = RaMa, place = pds, division = div1Gr1bDz1, period = semestLetniPeriod)
        d1bTue10a = createTimetable(dayOfWeek = 2, lesson = l10, subject = wychowanieFizyczne, teacher = RaMa, place = pds, division = div1Gr1bDz1, period = semestLetniPeriod)

        d1bWen1 = createTimetable(dayOfWeek = 3, lesson = l1, subject = matematyka, teacher = MiJo, place = p16, division = class1b, period = semestLetniPeriod)
        d1bWen2a = createTimetable(dayOfWeek = 3, lesson = l2, subject = jNiemiecki, teacher = PrKa, place = p25, division = divNie1G1, period = semestLetniPeriod)
        d1bWen3a = createTimetable(dayOfWeek = 3, lesson = l3, subject = jAngielski, teacher = KaMo, place = p10, division = div1bG1, period = semestLetniPeriod)
        d1bWen3b = createTimetable(dayOfWeek = 3, lesson = l3, subject = informatyka, teacher = DzMa, place = p4, division = div1bG2, period = semestLetniPeriod)
        d1bWen4a = createTimetable(dayOfWeek = 3, lesson = l4, subject = informatyka, teacher = DzMa, place = p4, division = div1bG2, period = semestLetniPeriod)
        d1bWen4b = createTimetable(dayOfWeek = 3, lesson = l4, subject = jAngielski, teacher = KiBe, place = p14, division = div1bG2, period = semestLetniPeriod)
        d1bWen5 = createTimetable(dayOfWeek = 3, lesson = l5, subject = geografia, teacher = GoTa, place = p20, division = class1b, period = semestLetniPeriod)
        d1bWen6 = createTimetable(dayOfWeek = 3, lesson = l6, subject = wiedzaOKulturze, teacher = TwMa, place = p13, division = class1b, period = semestLetniPeriod)
        d1bWen7 = createTimetable(dayOfWeek = 3, lesson = l7, subject = godzWych, teacher = KlLu, place = p16, division = class1b, period = semestLetniPeriod)
        d1bWen8 = createTimetable(dayOfWeek = 3, lesson = l8, subject = religia, teacher = PiTa, place = p36, division = class1b, period = semestLetniPeriod)


        d1bThu1 = createTimetable(dayOfWeek = 4, lesson = l1, subject = fizyka, teacher = JóSt, place = p15, division = class1b, period = semestLetniPeriod)
        d1bThu2 = createTimetable(dayOfWeek = 4, lesson = l2, subject = podstawyPrzedsiębiorczości, teacher = SM, place = p14, division = class1b, period = semestLetniPeriod)
        d1bThu3 = createTimetable(dayOfWeek = 4, lesson = l3, subject = matematyka, teacher = MiJo, place = p22, division = class1b, period = semestLetniPeriod)
        d1bThu4 = createTimetable(dayOfWeek = 4, lesson = l4, subject = matematyka, teacher = MiJo, place = p22, division = class1b, period = semestLetniPeriod)
        d1bThu5 = createTimetable(dayOfWeek = 4, lesson = l5, subject = religia, teacher = PiTa, place = p10, division = class1b, period = semestLetniPeriod)
        d1bThu6 = createTimetable(dayOfWeek = 1, lesson = l6, subject = historia, teacher = SwEl, place = p36, division = class1b, period = semestLetniPeriod)
        d1bThu7 = createTimetable(dayOfWeek = 1, lesson = l7, subject = wychowaniedoZyciaWRodzinie, teacher = SoRy, place = p36, division = class1b, period = semestLetniPeriod)


        d1bFri4 = createTimetable(dayOfWeek = 5, lesson = l4, subject = matematyka, teacher = MiJo, place = p7, division = class1b, period = semestLetniPeriod)
        d1bFri5a = createTimetable(dayOfWeek = 5, lesson = l5, subject = jAngielski, teacher = KaMo, place = p11, division = div1bG1, period = semestLetniPeriod)
        d1bFri5b = createTimetable(dayOfWeek = 5, lesson = l5, subject = jAngielski, teacher = KiBe, place = p13, division = div1bG2, period = semestLetniPeriod)
        d1bFri6 = createTimetable(dayOfWeek = 5, lesson = l6, subject = jPolski, teacher = KlLu, place = p15, division = class1b, period = semestLetniPeriod)
        d1bFri7a = createTimetable(dayOfWeek = 5, lesson = l7, subject = jNiemiecki, teacher = PrKa, place = p22, division = divNie1G1, period = semestLetniPeriod)

        // =====================================================
        // Curriculum
        // =====================================================

        val curriculum1aJPolski = createCurriculum(division = class1aGenerate, subject = jPolski, teacher = GrRe, numberOfActivities = 3) // kolor szary w planie - nie znam
        val curriculum1a1grJAngielski = createCurriculum(division = class1aGenerate, subject = jAngielski, teacher = TrAg, numberOfActivities = 3)
        val curriculum1a2grJAngielski = createCurriculum(division = class1aGenerate, subject = jAngielski, teacher = KoIr, numberOfActivities = 3)
        val curriculum1aGr1Jdodatkowy = createCurriculum(division = class1aGenerate, subject = jNiemiecki, teacher = HaAn, numberOfActivities = 3)
        val curriculum1aGr2Jdodatkowy = createCurriculum(division = class1aGenerate, subject = jFrancuski, teacher = RaBe, numberOfActivities = 3)
        val curriculum1aGr3Jdodatkowy = createCurriculum(division = class1aGenerate, subject = jRosyjski, teacher = ŁoDo, numberOfActivities = 3)
        val curriculum1aWiedzaOKulturze = createCurriculum(division = class1aGenerate, subject = wiedzaOKulturze, teacher = TwMa, numberOfActivities = 1)
        val curriculum1aHistoria = createCurriculum(division = class1aGenerate, subject = historia, teacher = SwEl, numberOfActivities = 2)
        val curriculum1aWiedzaOSpołeczeństwie = createCurriculum(division = class1aGenerate, subject = wiedzaOSpołeczeństwie, teacher = SwEl, numberOfActivities = 1)
        val curriculum1aPodstawyPrzedsiębiorczości = createCurriculum(division = class1aGenerate, subject = podstawyPrzedsiębiorczości, teacher = SM, numberOfActivities = 2)
        val curriculum1aGeografia = createCurriculum(division = class1aGenerate, subject = geografia, teacher = GoTa, numberOfActivities = 1)
        val curriculum1aBiologia = createCurriculum(division = class1aGenerate, subject = biologia, teacher = GuMa, numberOfActivities = 1)
        val curriculum1aChemia = createCurriculum(division = class1aGenerate, subject = chemia, teacher = ChAg, numberOfActivities = 1)
        val curriculum1aFizyka = createCurriculum(division = class1aGenerate, subject = fizyka, teacher = SzIr, numberOfActivities = 2)
        val curriculum1aMatematyka = createCurriculum(division = class1aGenerate, subject = matematyka, teacher = MiJo, numberOfActivities = 4)
        val curriculum1aInformatyka = createCurriculum(division = class1aGenerate, subject = informatyka, teacher = DzMa, numberOfActivities = 1)

        val curriculum1aGrDzWychowanieFizyczne = createCurriculum(division = class1aGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum1aGrChWychowanieFizyczne = createCurriculum(division = class1aGenerate, subject = wychowanieFizyczne, teacher = RaMa, numberOfActivities = 3)

        val curriculum1aEdukacjaDoBezpieczeństwa = createCurriculum(division = class1aGenerate, subject = edukacjaDoBezpieczeństwa, teacher = BlSł, numberOfActivities = 1)
        val curriculum1aGodzWych = createCurriculum(division = class1aGenerate, subject = godzWych, teacher = DzMa, numberOfActivities = 1)
        val curriculum1aWychowaniedoZyciaWRodzinie = createCurriculum(division = class1aGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = SoRy, numberOfActivities = 1)
        val curriculum1aReligia = createCurriculum(division = class1aGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)



        val curriculum1bJPolski = createCurriculum(division = class1bGenerate, subject = jPolski, teacher = GrRe, numberOfActivities = 3)
        val curriculum1b1grJAngielski = createCurriculum(division = class1bGenerate, subject = jAngielski, teacher = KoIr, numberOfActivities = 3)
        val curriculum1b2grJAngielski = createCurriculum(division = class1bGenerate, subject = jAngielski, teacher = KaTo, numberOfActivities = 3)
        val curriculum1bGr1Jdodatkowy = createCurriculum(division = class1bGenerate, subject = jNiemiecki, teacher = PrAn, numberOfActivities = 3)
        val curriculum1bGr2Jdodatkowy = createCurriculum(division = class1bGenerate, subject = jNiemiecki, teacher = HaAn, numberOfActivities = 3)
        val curriculum1bGr3Jdodatkowy = createCurriculum(division = class1bGenerate, subject = jFrancuski, teacher = RaBe, numberOfActivities = 3)
        val curriculum1bGr4Jdodatkowy = createCurriculum(division = class1bGenerate, subject = jRosyjski, teacher = ŁoDo, numberOfActivities = 3)
        val curriculum1bWiedzaOKulturze = createCurriculum(division = class1bGenerate, subject = wiedzaOKulturze, teacher = TwMa, numberOfActivities = 1)
        val curriculum1bHistoria = createCurriculum(division = class1bGenerate, subject = historia, teacher = DyLu, numberOfActivities = 2)
        val curriculum1bWiedzaOSpołeczeństwie = createCurriculum(division = class1bGenerate, subject = wiedzaOSpołeczeństwie, teacher = DyLu, numberOfActivities = 1)
        val curriculum1bPodstawyPrzedsiębiorczości = createCurriculum(division = class1bGenerate, subject = podstawyPrzedsiębiorczości, teacher = SM, numberOfActivities = 2)
        val curriculum1bGeografia = createCurriculum(division = class1bGenerate, subject = geografia, teacher = GoTa, numberOfActivities = 2)
        val curriculum1bBiologia = createCurriculum(division = class1bGenerate, subject = biologia, teacher = GuMa, numberOfActivities = 1)
        val curriculum1bChemia = createCurriculum(division = class1bGenerate, subject = chemia, teacher = ChAg, numberOfActivities = 1)
        val curriculum1bFizyka = createCurriculum(division = class1bGenerate, subject = fizyka, teacher = JóSt, numberOfActivities = 1)
        val curriculum1bMatematyka = createCurriculum(division = class1bGenerate, subject = matematyka, teacher = MiJo, numberOfActivities = 4)
        val curriculum1bInformatyka = createCurriculum(division = class1bGenerate, subject = informatyka, teacher = DzMa, numberOfActivities = 1)
        val curriculum1bGrDz1WychowanieFizyczne = createCurriculum(division = class1bGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum1bGrDz2WychowanieFizyczne = createCurriculum(division = class1bGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum1bGrChWychowanieFizyczne = createCurriculum(division = class1bGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum1bEdukacjaDoBezpieczeństwa = createCurriculum(division = class1bGenerate, subject = edukacjaDoBezpieczeństwa, teacher = BlSł, numberOfActivities = 1)
        val curriculum1bGodzWych = createCurriculum(division = class1bGenerate, subject = godzWych, teacher = DyLu, numberOfActivities = 1)
        val curriculum1bWychowaniedoZyciaWRodzinie = createCurriculum(division = class1bGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = SoRy, numberOfActivities = 1)
        val curriculum1bReligia = createCurriculum(division = class1bGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)

        val curriculum1cJPolski = createCurriculum(division = class1cGenerate, subject = jPolski, teacher = PeRe, numberOfActivities = 3)
        val curriculum1c1grJAngielski = createCurriculum(division = class1cGenerate, subject = jAngielski, teacher = KoIr, numberOfActivities = 3)
        val curriculum1c2grJAngielski = createCurriculum(division = class1cGenerate, subject = jAngielski, teacher = KaTo, numberOfActivities = 3)
        val curriculum1cGr1Jdodatkowy = createCurriculum(division = class1cGenerate, subject = jNiemiecki, teacher = PrKa, numberOfActivities = 3)
        val curriculum1cGr2Jdodatkowy = createCurriculum(division = class1cGenerate, subject = jRosyjski, teacher = StBo, numberOfActivities = 3)
        val curriculum1cGr3Jdodatkowy = createCurriculum(division = class1cGenerate, subject = jFrancuski, teacher = RaBe, numberOfActivities = 3)
        val curriculum1cWiedzaOKulturze = createCurriculum(division = class1cGenerate, subject = wiedzaOKulturze, teacher = TwMa, numberOfActivities = 1)
        val curriculum1cHistoria = createCurriculum(division = class1cGenerate, subject = historia, teacher = LoEl, numberOfActivities = 3)
        val curriculum1cWiedzaOSpołeczeństwie = createCurriculum(division = class1cGenerate, subject = wiedzaOSpołeczeństwie, teacher = LoEl, numberOfActivities = 1)
        val curriculum1cPodstawyPrzedsiębiorczości = createCurriculum(division = class1cGenerate, subject = podstawyPrzedsiębiorczości, teacher = SuZb, numberOfActivities = 2)
        val curriculum1cGeografia = createCurriculum(division = class1cGenerate, subject = geografia, teacher = GoTa, numberOfActivities = 1)
        val curriculum1cBiologia = createCurriculum(division = class1cGenerate, subject = biologia, teacher = DeWi, numberOfActivities = 1)
        val curriculum1cChemia = createCurriculum(division = class1cGenerate, subject = chemia, teacher = ChAg, numberOfActivities = 1)
        val curriculum1cFizyka = createCurriculum(division = class1cGenerate, subject = fizyka, teacher = JóSt, numberOfActivities = 1)
        val curriculum1cMatematyka = createCurriculum(division = class1cGenerate, subject = matematyka, teacher = JóSt, numberOfActivities = 3)
        val curriculum1cInformatyka = createCurriculum(division = class1cGenerate, subject = informatyka, teacher = DzMa, numberOfActivities = 1)
        val curriculum1cGrDz1WychowanieFizyczne = createCurriculum(division = class1cGenerate, subject = wychowanieFizyczne, teacher = RaMa, numberOfActivities = 3)
        val curriculum1cGrDz2WychowanieFizyczne = createCurriculum(division = class1cGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum1cGrChWychowanieFizyczne = createCurriculum(division = class1cGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum1cEdukacjaDoBezpieczeństwa = createCurriculum(division = class1cGenerate, subject = edukacjaDoBezpieczeństwa, teacher = BlSł, numberOfActivities = 1)
        val curriculum1cGodzWych = createCurriculum(division = class1cGenerate, subject = godzWych, teacher = LoEl, numberOfActivities = 1)
        val curriculum1cWychowaniedoZyciaWRodzinie = createCurriculum(division = class1cGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = SoRy, numberOfActivities = 1)
        val curriculum1cReligia = createCurriculum(division = class1cGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)


        val curriculum1dJPolski = createCurriculum(division = class1dGenerate, subject = jPolski, teacher = PrAr, numberOfActivities = 4)
        val curriculum1d1grJAngielski = createCurriculum(division = class1dGenerate, subject = jAngielski, teacher = TrAg, numberOfActivities = 3)
        val curriculum1d2grJAngielski = createCurriculum(division = class1dGenerate, subject = jAngielski, teacher = KaMo, numberOfActivities = 3)
        val curriculum1dGr1Jdodatkowy = createCurriculum(division = class1dGenerate, subject = jNiemiecki, teacher = PrAn, numberOfActivities = 3)
        val curriculum1dGr2Jdodatkowy = createCurriculum(division = class1dGenerate, subject = jFrancuski, teacher = JaEd, numberOfActivities = 3)
        val curriculum1dWiedzaOKulturze = createCurriculum(division = class1dGenerate, subject = wiedzaOKulturze, teacher = TwMa, numberOfActivities = 1)
        val curriculum1dHistoria = createCurriculum(division = class1dGenerate, subject = historia, teacher = SwEl, numberOfActivities = 3)
        val curriculum1dWiedzaOSpołeczeństwie = createCurriculum(division = class1dGenerate, subject = wiedzaOSpołeczeństwie, teacher = SwEl, numberOfActivities = 1)
        val curriculum1dPodstawyPrzedsiębiorczości = createCurriculum(division = class1dGenerate, subject = podstawyPrzedsiębiorczości, teacher = SuZb, numberOfActivities = 2)
        val curriculum1dGeografia = createCurriculum(division = class1dGenerate, subject = geografia, teacher = GoTa, numberOfActivities = 1)
        val curriculum1dBiologia = createCurriculum(division = class1dGenerate, subject = biologia, teacher = DeWi, numberOfActivities = 1)
        val curriculum1dChemia = createCurriculum(division = class1dGenerate, subject = chemia, teacher = ChAg, numberOfActivities = 1)
        val curriculum1dFizyka = createCurriculum(division = class1dGenerate, subject = fizyka, teacher = JóSt, numberOfActivities = 1)
        val curriculum1dMatematyka = createCurriculum(division = class1dGenerate, subject = matematyka, teacher = JaMa, numberOfActivities = 3)
        val curriculum1dInformatyka = createCurriculum(division = class1dGenerate, subject = informatyka, teacher = DzMa, numberOfActivities = 1)
        val curriculum1dGrDz1WychowanieFizyczne = createCurriculum(division = class1dGenerate, subject = wychowanieFizyczne, teacher = RaMa, numberOfActivities = 3)
        val curriculum1dGrDz2WychowanieFizyczne = createCurriculum(division = class1dGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum1dGrChWychowanieFizyczne = createCurriculum(division = class1dGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum1dEdukacjaDoBezpieczeństwa = createCurriculum(division = class1dGenerate, subject = edukacjaDoBezpieczeństwa, teacher = BlSł, numberOfActivities = 1)
        val curriculum1dGodzWych = createCurriculum(division = class1dGenerate, subject = godzWych, teacher = SwEl, numberOfActivities = 1)
        val curriculum1dWychowaniedoZyciaWRodzinie = createCurriculum(division = class1dGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = SoRy, numberOfActivities = 1)
        val curriculum1dReligia = createCurriculum(division = class1dGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)

        val curriculum1eJPolski = createCurriculum(division = class1eGenerate, subject = jPolski, teacher = PeRe, numberOfActivities = 3)
        val curriculum1e1grJAngielski = createCurriculum(division = class1eGenerate, subject = jAngielski, teacher = KiBe, numberOfActivities = 3)
        val curriculum1e2grJAngielski = createCurriculum(division = class1eGenerate, subject = jAngielski, teacher = KaMo, numberOfActivities = 3) // dziwny podzial grup na planie
        val curriculum1eGr1Jdodatkowy = createCurriculum(division = class1eGenerate, subject = jNiemiecki, teacher = HaAn, numberOfActivities = 3)
        val curriculum1eGr2Jdodatkowy = createCurriculum(division = class1eGenerate, subject = jNiemiecki, teacher = PrAn, numberOfActivities = 3)
        val curriculum1eGr3Jdodatkowy = createCurriculum(division = class1eGenerate, subject = jFrancuski, teacher = RaBe, numberOfActivities = 3)
        val curriculum1eWiedzaOKulturze = createCurriculum(division = class1eGenerate, subject = wiedzaOKulturze, teacher = TwMa, numberOfActivities = 1)
        val curriculum1eHistoria = createCurriculum(division = class1eGenerate, subject = historia, teacher = LoEl, numberOfActivities = 2)
        val curriculum1eWiedzaOSpołeczeństwie = createCurriculum(division = class1eGenerate, subject = wiedzaOSpołeczeństwie, teacher = LoEl, numberOfActivities = 1)
        val curriculum1ePodstawyPrzedsiębiorczości = createCurriculum(division = class1eGenerate, subject = podstawyPrzedsiębiorczości, teacher = SM, numberOfActivities = 2)
        val curriculum1eGeografia = createCurriculum(division = class1eGenerate, subject = geografia, teacher = GoTa, numberOfActivities = 1)
        val curriculum1eBiologia = createCurriculum(division = class1eGenerate, subject = biologia, teacher = GuMa, numberOfActivities = 2)
        val curriculum1eChemia = createCurriculum(division = class1eGenerate, subject = chemia, teacher = ChAg, numberOfActivities = 2)
        val curriculum1eFizyka = createCurriculum(division = class1eGenerate, subject = fizyka, teacher = BlSł, numberOfActivities = 1)
        val curriculum1eMatematyka = createCurriculum(division = class1eGenerate, subject = matematyka, teacher = JaMa, numberOfActivities = 3)
        val curriculum1eInformatyka = createCurriculum(division = class1eGenerate, subject = informatyka, teacher = SoRy, numberOfActivities = 1) // dziwny podzial grup na planie: Solecki i Dzierwa
        val curriculum1eGrDzWychowanieFizyczne = createCurriculum(division = class1eGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum1eGrChWychowanieFizyczne = createCurriculum(division = class1eGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum1eEdukacjaDoBezpieczeństwa = createCurriculum(division = class1eGenerate, subject = edukacjaDoBezpieczeństwa, teacher = BlSł, numberOfActivities = 1)
        val curriculum1eGodzWych = createCurriculum(division = class1eGenerate, subject = godzWych, teacher = KiBe, numberOfActivities = 1)
        val curriculum1eWychowaniedoZyciaWRodzinie = createCurriculum(division = class1eGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = SoRy, numberOfActivities = 1)
        val curriculum1eReligia = createCurriculum(division = class1eGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)

        val curriculum1fJPolski = createCurriculum(division = class1fGenerate, subject = jPolski, teacher = PrAr, numberOfActivities = 3)
        val curriculum1f1grJAngielski = createCurriculum(division = class1fGenerate, subject = jAngielski, teacher = KoIr, numberOfActivities = 3)
        val curriculum1f2grJAngielski = createCurriculum(division = class1fGenerate, subject = jAngielski, teacher = KiBe, numberOfActivities = 3)
        val curriculum1fGr1Jdodatkowy = createCurriculum(division = class1fGenerate, subject = jNiemiecki, teacher = PrKa, numberOfActivities = 3)
        val curriculum1fGr2Jdodatkowy = createCurriculum(division = class1fGenerate, subject = jNiemiecki, teacher = PrAn, numberOfActivities = 3)
        val curriculum1fGr3Jdodatkowy = createCurriculum(division = class1fGenerate, subject = jFrancuski, teacher = RaBe, numberOfActivities = 3)
        val curriculum1fWiedzaOKulturze = createCurriculum(division = class1fGenerate, subject = wiedzaOKulturze, teacher = TwMa, numberOfActivities = 1)
        val curriculum1fHistoria = createCurriculum(division = class1fGenerate, subject = historia, teacher = SwEl, numberOfActivities = 2)
        val curriculum1fWiedzaOSpołeczeństwie = createCurriculum(division = class1fGenerate, subject = wiedzaOSpołeczeństwie, teacher = DyLu, numberOfActivities = 1)
        val curriculum1fPodstawyPrzedsiębiorczości = createCurriculum(division = class1fGenerate, subject = podstawyPrzedsiębiorczości, teacher = SuZb, numberOfActivities = 2)
        val curriculum1fGeografia = createCurriculum(division = class1fGenerate, subject = geografia, teacher = GoTa, numberOfActivities = 1)
        val curriculum1fBiologia = createCurriculum(division = class1fGenerate, subject = biologia, teacher = GuMa, numberOfActivities = 2)
        val curriculum1fChemia = createCurriculum(division = class1fGenerate, subject = chemia, teacher = SeMo, numberOfActivities = 2)
        val curriculum1fFizyka = createCurriculum(division = class1fGenerate, subject = fizyka, teacher = BlSł, numberOfActivities = 1)
        val curriculum1fMatematyka = createCurriculum(division = class1fGenerate, subject = matematyka, teacher = JaMa, numberOfActivities = 3)
        val curriculum1fInformatyka = createCurriculum(division = class1fGenerate, subject = informatyka, teacher = DzMa, numberOfActivities = 1)
        val curriculum1fGrDzWychowanieFizyczne = createCurriculum(division = class1fGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum1fGrChWychowanieFizyczne = createCurriculum(division = class1fGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum1fEdukacjaDoBezpieczeństwa = createCurriculum(division = class1fGenerate, subject = edukacjaDoBezpieczeństwa, teacher = BlSł, numberOfActivities = 1)
        val curriculum1fGodzWych = createCurriculum(division = class1fGenerate, subject = godzWych, teacher = KiBe, numberOfActivities = 1)
        val curriculum1fWychowaniedoZyciaWRodzinie = createCurriculum(division = class1fGenerate, subject = wychowaniedoZyciaWRodzinie, teacher = SoRy, numberOfActivities = 1)
        val curriculum1fReligia = createCurriculum(division = class1fGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)

        val curriculum2aJPolski = createCurriculum(division = class2aGenerate, subject = jPolski, teacher = PeRe, numberOfActivities = 5)
        val curriculum2a1grJAngielski = createCurriculum(division = class2aGenerate, subject = jAngielski, teacher = KiBe, numberOfActivities = 3)
        val curriculum2a2grJAngielski = createCurriculum(division = class2aGenerate, subject = jAngielski, teacher = KaTo, numberOfActivities = 3)
        val curriculum2aGr1Jdodatkowy = createCurriculum(division = class2aGenerate, subject = jNiemiecki, teacher = HaAn, numberOfActivities = 3)
        val curriculum2aGr2Jdodatkowy = createCurriculum(division = class2aGenerate, subject = jFrancuski, teacher = RaBe, numberOfActivities = 3)
        val curriculum2aGr3Jdodatkowy = createCurriculum(division = class2aGenerate, subject = jRosyjski, teacher = ŁoDo, numberOfActivities = 3)
        val curriculum2aGr4Jdodatkowy = createCurriculum(division = class2aGenerate, subject = jRosyjski, teacher = StBo, numberOfActivities = 3)
        val curriculum2aFizyka = createCurriculum(division = class2aGenerate, subject = fizyka, teacher = SzIr, numberOfActivities = 4)
        val curriculum2aMatematyka = createCurriculum(division = class2aGenerate, subject = matematyka, teacher = CzBe, numberOfActivities = 7)
        val curriculum2aInformatyka = createCurriculum(division = class2aGenerate, subject = informatyka, teacher = DzMa, numberOfActivities = 3)
        val curriculum2aGrDzWychowanieFizyczne = createCurriculum(division = class2aGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum2aGrChWychowanieFizyczne = createCurriculum(division = class2aGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum2aGodzWych = createCurriculum(division = class2aGenerate, subject = godzWych, teacher = CzBe, numberOfActivities = 1)
        val curriculum2aHistISpol = createCurriculum(division = class2aGenerate, subject = historiaISpoleczenstwo, teacher = LoEl, numberOfActivities = 2)
        val curriculum2aTechAngielski = createCurriculum(division = class2aGenerate, subject = techAngielski, teacher = SzIr, numberOfActivities = 1)
        val curriculum2aReligia = createCurriculum(division = class2aGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)

        val curriculum2bJPolski = createCurriculum(division = class2bGenerate, subject = jPolski, teacher = KlLu, numberOfActivities = 5)
        val curriculum2b1grJAngielski = createCurriculum(division = class2bGenerate, subject = jAngielski, teacher = KaMo, numberOfActivities = 3)
        val curriculum2b2grJAngielski = createCurriculum(division = class2bGenerate, subject = jAngielski, teacher = KiBe, numberOfActivities = 3)
        val curriculum2bGr1Jdodatkowy = createCurriculum(division = class2bGenerate, subject = jNiemiecki, teacher = PrKa, numberOfActivities = 3)
        val curriculum2bGr2Jdodatkowy = createCurriculum(division = class2bGenerate, subject = jNiemiecki, teacher = HaAn, numberOfActivities = 3)
        val curriculum2bGr3Jdodatkowy = createCurriculum(division = class2bGenerate, subject = jFrancuski, teacher = RaBe, numberOfActivities = 3)
        val curriculum2bGr4Jdodatkowy = createCurriculum(division = class2bGenerate, subject = jRosyjski, teacher = ŁoDo, numberOfActivities = 3)
        val curriculum2bGeografia = createCurriculum(division = class2bGenerate, subject = geografia, teacher = GoTa, numberOfActivities = 5)
        val curriculum2bMatematyka = createCurriculum(division = class2bGenerate, subject = matematyka, teacher = MiJo, numberOfActivities = 8)
        val curriculum2bGrDzWychowanieFizyczne = createCurriculum(division = class2bGenerate, subject = wychowanieFizyczne, teacher = DaRe, numberOfActivities = 3)
        val curriculum2bGrChWychowanieFizyczne = createCurriculum(division = class2bGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum2bGodzWych = createCurriculum(division = class2bGenerate, subject = godzWych, teacher = KlLu, numberOfActivities = 1)
        val curriculum2bHistISpol = createCurriculum(division = class2bGenerate, subject = historiaISpoleczenstwo, teacher = DyLu, numberOfActivities = 2)
        val curriculum2bEkonomiaWPraktyce = createCurriculum(division = class2bGenerate, subject = ekonomiaWPraktyce, teacher = SM, numberOfActivities = 1)
        val curriculum2bReligia = createCurriculum(division = class2bGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)

        val curriculum2cJPolski = createCurriculum(division = class2cGenerate, subject = jPolski, teacher = KlLu, numberOfActivities = 8)
        val curriculum2c1grJAngielski = createCurriculum(division = class2cGenerate, subject = jAngielski, teacher = PrAn, numberOfActivities = 3)
        val curriculum2c2grJAngielski = createCurriculum(division = class2cGenerate, subject = jAngielski, teacher = KoIr, numberOfActivities = 3)
        val curriculum2cGr1Jdodatkowy = createCurriculum(division = class2cGenerate, subject = jNiemiecki, teacher = PrKa, numberOfActivities = 3)
        val curriculum2cGr2Jdodatkowy = createCurriculum(division = class2cGenerate, subject = jFrancuski, teacher = RaBe, numberOfActivities = 3)
        val curriculum2cGr3Jdodatkowy = createCurriculum(division = class2cGenerate, subject = jRosyjski, teacher = ŁoDo, numberOfActivities = 3)
        val curriculum2cMatematyka = createCurriculum(division = class2cGenerate, subject = matematyka, teacher = JóSt, numberOfActivities = 4)
        val curriculum2cGrDz1WychowanieFizyczne = createCurriculum(division = class2cGenerate, subject = wychowanieFizyczne, teacher = DaRe, numberOfActivities = 3)
        val curriculum2cGrDz2WychowanieFizyczne = createCurriculum(division = class2cGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum2cGrChWychowanieFizyczne = createCurriculum(division = class2cGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum2cGodzWych = createCurriculum(division = class2cGenerate, subject = godzWych, teacher = BlSł, numberOfActivities = 1)
        val curriculum2cHistoria = createCurriculum(division = class2cGenerate, subject = historia, teacher = DyLu, numberOfActivities = 4)
        val curriculum2cWiedzaOSpoleczenstwie = createCurriculum(division = class2cGenerate, subject = wiedzaOSpołeczeństwie, teacher = DyLu, numberOfActivities = 3)
        val curriculum2cPrzyroda = createCurriculum(division = class2cGenerate, subject = przyroda, teacher = BlSł, numberOfActivities = 2)
        val curriculum2cLacinaPrawnicza = createCurriculum(division = class2cGenerate, subject = jLacińskiPrawniczy, teacher = KuKa, numberOfActivities = 1)
        val curriculum2cReligia = createCurriculum(division = class2cGenerate, subject = religia, teacher = PiTa, numberOfActivities = 2)

        val curriculum2dJPolski = createCurriculum(division = class2dGenerate, subject = jPolski, teacher = PeRe, numberOfActivities = 4)
        val curriculum2d1grJAngielski = createCurriculum(division = class2dGenerate, subject = jAngielski, teacher = PrAn, numberOfActivities = 6)
        val curriculum2d2grJAngielski = createCurriculum(division = class2dGenerate, subject = jAngielski, teacher = TrAg, numberOfActivities = 6)
        val curriculum2dGr1Jdodatkowy = createCurriculum(division = class2dGenerate, subject = jNiemiecki, teacher = HaAn, numberOfActivities = 6)
        val curriculum2dGr2Jdodatkowy = createCurriculum(division = class2dGenerate, subject = jFrancuski, teacher = JaEd, numberOfActivities = 6)
        val curriculum2dMatematyka = createCurriculum(division = class2dGenerate, subject = matematyka, teacher = JóSt, numberOfActivities = 4)
        val curriculum2dGrDzWychowanieFizyczne = createCurriculum(division = class2dGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum2dGrChWychowanieFizyczne = createCurriculum(division = class2dGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum2dGodzWych = createCurriculum(division = class2dGenerate, subject = godzWych, teacher = PeRe, numberOfActivities = 1)
        val curriculum2dHistoria = createCurriculum(division = class2dGenerate, subject = historia, teacher = SwEl, numberOfActivities = 2)
        val curriculum2dPrzyroda = createCurriculum(division = class2dGenerate, subject = przyroda, teacher = GoTa, numberOfActivities = 2)
        val curriculum2dEdukacjaDziennikarska = createCurriculum(division = class2dGenerate, subject = edukacjaDziennikarska, teacher = PrAr, numberOfActivities = 1)
        val curriculum2dLiteraturaObca = createCurriculum(division = class2dGenerate, subject = literaturaObca, teacher = PeRe, numberOfActivities = 1)
        val curriculum2dReligia = createCurriculum(division = class2dGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)


        val curriculum2eJPolski = createCurriculum(division = class2eGenerate, subject = jPolski, teacher = GrRe, numberOfActivities = 5)
        val curriculum2e1grJAngielski = createCurriculum(division = class2eGenerate, subject = jAngielski, teacher = KoIr, numberOfActivities = 3)
        val curriculum2e2grJAngielski = createCurriculum(division = class2eGenerate, subject = jAngielski, teacher = KiBe, numberOfActivities = 3)
        val curriculum2eGr1Jdodatkowy = createCurriculum(division = class2eGenerate, subject = jNiemiecki, teacher = PrAn, numberOfActivities = 3)
        val curriculum2eGr2Jdodatkowy = createCurriculum(division = class2eGenerate, subject = jNiemiecki, teacher = PrKa, numberOfActivities = 3)
        val curriculum2eGr3Jdodatkowy = createCurriculum(division = class2eGenerate, subject = jFrancuski, teacher = JaEd, numberOfActivities = 3)
        val curriculum2eMatematyka = createCurriculum(division = class2eGenerate, subject = matematyka, teacher = JaMa, numberOfActivities = 4)
        val curriculum2eGrDz1WychowanieFizyczne = createCurriculum(division = class2eGenerate, subject = wychowanieFizyczne, teacher = DaRe, numberOfActivities = 3)
        val curriculum2eGrDz2WychowanieFizyczne = createCurriculum(division = class2eGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum2eGrChWychowanieFizyczne = createCurriculum(division = class2eGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum2eGodzWych = createCurriculum(division = class2eGenerate, subject = godzWych, teacher = JaMa, numberOfActivities = 1)
        val curriculum2eBiologia = createCurriculum(division = class2eGenerate, subject = biologia, teacher = SzTo, numberOfActivities = 5)
        val curriculum2eChemia = createCurriculum(division = class2eGenerate, subject = chemia, teacher = SeMo, numberOfActivities = 5)
        val curriculum2eHistISpol = createCurriculum(division = class2eGenerate, subject = historiaISpoleczenstwo, teacher = DyLu, numberOfActivities = 2)
        val curriculum2eFizykaMedyczna = createCurriculum(division = class2eGenerate, subject = fizykaMedyczna, teacher = SzIr, numberOfActivities = 1)
        val curriculum2eReligia = createCurriculum(division = class2eGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)

        val curriculum2fJPolski = createCurriculum(division = class2fGenerate, subject = jPolski, teacher = GrRe, numberOfActivities = 5)
        val curriculum2f1grJAngielski = createCurriculum(division = class2fGenerate, subject = jAngielski, teacher = KaTo, numberOfActivities = 3)
        val curriculum2f2grJAngielski = createCurriculum(division = class2fGenerate, subject = jAngielski, teacher = TrAg, numberOfActivities = 3)
        val curriculum2fGr1Jdodatkowy = createCurriculum(division = class2fGenerate, subject = jNiemiecki, teacher = PrKa, numberOfActivities = 3)
        val curriculum2fGr2Jdodatkowy = createCurriculum(division = class2fGenerate, subject = jNiemiecki, teacher = HaAn, numberOfActivities = 3)
        val curriculum2fGr3Jdodatkowy = createCurriculum(division = class2fGenerate, subject = jFrancuski, teacher = JaEd, numberOfActivities = 3)
        val curriculum2fMatematyka = createCurriculum(division = class2fGenerate, subject = matematyka, teacher = CzBe, numberOfActivities = 7)
        val curriculum2fGrDz1WychowanieFizyczne = createCurriculum(division = class2fGenerate, subject = wychowanieFizyczne, teacher = DaRe, numberOfActivities = 3)
        val curriculum2fGrDz2WychowanieFizyczne = createCurriculum(division = class2fGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum2fGrChWychowanieFizyczne = createCurriculum(division = class2fGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum2fGodzWych = createCurriculum(division = class2fGenerate, subject = godzWych, teacher = ChAg, numberOfActivities = 1)
        val curriculum2fBiologia = createCurriculum(division = class2fGenerate, subject = biologia, teacher = SzTo, numberOfActivities = 4)
        val curriculum2fChemia = createCurriculum(division = class2fGenerate, subject = chemia, teacher = ChAg, numberOfActivities = 4)
        val curriculum2fHistISpol = createCurriculum(division = class2fGenerate, subject = historiaISpoleczenstwo, teacher = DyLu, numberOfActivities = 2)
        val curriculum2fReligia = createCurriculum(division = class2fGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)


        val curriculum3aJPolski = createCurriculum(division = class3aGenerate, subject = jPolski, teacher = KlLu, numberOfActivities = 4)
        val curriculum3a1grJAngielski = createCurriculum(division = class3aGenerate, subject = jAngielski, teacher = KaMo, numberOfActivities = 3)
        val curriculum3a2grJAngielski = createCurriculum(division = class3aGenerate, subject = jAngielski, teacher = KaTo, numberOfActivities = 3)
        val curriculum3aFizyka = createCurriculum(division = class3aGenerate, subject = fizyka, teacher = SzIr, numberOfActivities = 4)
        val curriculum3aMatematyka = createCurriculum(division = class3aGenerate, subject = matematyka, teacher = CzBe, numberOfActivities = 8)
        val curriculum3aInformatyka = createCurriculum(division = class3aGenerate, subject = informatyka, teacher = SoRy, numberOfActivities = 3)
        val curriculum3aGrDzWychowanieFizyczne = createCurriculum(division = class3aGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum3aGrChWychowanieFizyczne = createCurriculum(division = class3aGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum3aGodzWych = createCurriculum(division = class3aGenerate, subject = godzWych, teacher = SzIr, numberOfActivities = 1)
        val curriculum3aHistISpol = createCurriculum(division = class3aGenerate, subject = historiaISpoleczenstwo, teacher = SwEl, numberOfActivities = 2)
        val curriculum3aTechAngielski = createCurriculum(division = class3aGenerate, subject = techAngielski, teacher = SzIr, numberOfActivities = 1)
        val curriculum3aReligia = createCurriculum(division = class3aGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)

        val curriculum3bJPolski = createCurriculum(division = class3bGenerate, subject = jPolski, teacher = KlLu, numberOfActivities = 4)
        val curriculum3b1grJAngielski = createCurriculum(division = class3bGenerate, subject = jAngielski, teacher = KaMo, numberOfActivities = 3)
        val curriculum3b2grJAngielski = createCurriculum(division = class3bGenerate, subject = jAngielski, teacher = KiBe, numberOfActivities = 3)
        val curriculum3bGeografia = createCurriculum(division = class3bGenerate, subject = geografia, teacher = GoTa, numberOfActivities = 5)
        val curriculum3bMatematyka = createCurriculum(division = class3bGenerate, subject = matematyka, teacher = MiJo, numberOfActivities = 9)
        val curriculum3bGrDz1WychowanieFizyczne = createCurriculum(division = class3bGenerate, subject = wychowanieFizyczne, teacher = DaRe, numberOfActivities = 3)
        val curriculum3bGrDz2WychowanieFizyczne = createCurriculum(division = class3bGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum3bGrChWychowanieFizyczne = createCurriculum(division = class3bGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum3bGodzWych = createCurriculum(division = class3bGenerate, subject = godzWych, teacher = GoTa, numberOfActivities = 1)
        val curriculum3bHistISpol = createCurriculum(division = class3bGenerate, subject = historiaISpoleczenstwo, teacher = SwEl, numberOfActivities = 2)
        val curriculum3bReligia = createCurriculum(division = class3bGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)

        val curriculum3cJPolski = createCurriculum(division = class3cGenerate, subject = jPolski, teacher = PrAr, numberOfActivities = 9)
        val curriculum3c1grJAngielski = createCurriculum(division = class3cGenerate, subject = jAngielski, teacher = TrAg, numberOfActivities = 3)
        val curriculum3c2grJAngielski = createCurriculum(division = class3cGenerate, subject = jAngielski, teacher = KaTo, numberOfActivities = 3)
        val curriculum3cMatematyka = createCurriculum(division = class3cGenerate, subject = matematyka, teacher = JóSt, numberOfActivities = 3)
        val curriculum3cGrDzWychowanieFizyczne = createCurriculum(division = class3cGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum3cGrChWychowanieFizyczne = createCurriculum(division = class3cGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum3cGodzWych = createCurriculum(division = class3cGenerate, subject = godzWych, teacher = WiWo, numberOfActivities = 1)
        val curriculum3cHistoria = createCurriculum(division = class3cGenerate, subject = historia, teacher = LoEl, numberOfActivities = 4)
        val curriculum3cWiedzaOSpoleczenstwie = createCurriculum(division = class3cGenerate, subject = wiedzaOSpołeczeństwie, teacher = LoEl, numberOfActivities = 4)
        val curriculum3cPrzyroda = createCurriculum(division = class3cGenerate, subject = przyroda, teacher = SzTo, numberOfActivities = 2)
        val curriculum3cReligia = createCurriculum(division = class3cGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)

        val curriculum3dJPolski = createCurriculum(division = class3dGenerate, subject = jPolski, teacher = PrAr, numberOfActivities = 4)
        val curriculum3d1grJAngielski = createCurriculum(division = class3dGenerate, subject = jAngielski, teacher = KaMo, numberOfActivities = 6)
        val curriculum3d2grJAngielski = createCurriculum(division = class3dGenerate, subject = jAngielski, teacher = TrAg, numberOfActivities = 6)
        val curriculum3dGr1Jdodatkowy = createCurriculum(division = class3dGenerate, subject = jNiemiecki, teacher = PrAn, numberOfActivities = 6)
        val curriculum3dGr2Jdodatkowy = createCurriculum(division = class3dGenerate, subject = jFrancuski, teacher = TwMa, numberOfActivities = 6)
        val curriculum3dMatematyka = createCurriculum(division = class3dGenerate, subject = matematyka, teacher = JóSt, numberOfActivities = 3)
        val curriculum3dGr1DzWychowanieFizyczne = createCurriculum(division = class3dGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum3dGr2DzWychowanieFizyczne = createCurriculum(division = class3dGenerate, subject = wychowanieFizyczne, teacher = WiWo, numberOfActivities = 3)
        val curriculum3dGrChWychowanieFizyczne = createCurriculum(division = class3dGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum3dGodzWych = createCurriculum(division = class3dGenerate, subject = godzWych, teacher = PrAr, numberOfActivities = 1)
        val curriculum3dHistoria = createCurriculum(division = class3dGenerate, subject = historia, teacher = SwEl, numberOfActivities = 3)
        val curriculum3dPrzyroda = createCurriculum(division = class3dGenerate, subject = przyroda, teacher = SeMo, numberOfActivities = 2)
        val curriculum3dEdukacjaDziennikarska = createCurriculum(division = class3dGenerate, subject = edukacjaDziennikarska, teacher = PrAr, numberOfActivities = 1)
        val curriculum3dLiteraturaObca = createCurriculum(division = class3dGenerate, subject = literaturaObca, teacher = PeRe, numberOfActivities = 1)
        val curriculum3dReligia = createCurriculum(division = class3dGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)

        val curriculum3eJPolski = createCurriculum(division = class3eGenerate, subject = jPolski, teacher = GrRe, numberOfActivities = 4)
        val curriculum3e1grJAngielski = createCurriculum(division = class3eGenerate, subject = jAngielski, teacher = KiBe, numberOfActivities = 3)
        val curriculum3e2grJAngielski = createCurriculum(division = class3eGenerate, subject = jAngielski, teacher = BaEl, numberOfActivities = 3)
        val curriculum3eMatematyka = createCurriculum(division = class3eGenerate, subject = matematyka, teacher = JaMa, numberOfActivities = 3)
        val curriculum3eGrDz1WychowanieFizyczne = createCurriculum(division = class3eGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum3eGrDz2WychowanieFizyczne = createCurriculum(division = class3eGenerate, subject = wychowanieFizyczne, teacher = DaRe, numberOfActivities = 3)
        val curriculum3eGrChWychowanieFizyczne = createCurriculum(division = class3eGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum3eGodzWych = createCurriculum(division = class3eGenerate, subject = godzWych, teacher = SzTo, numberOfActivities = 1)
        val curriculum3eBiologia = createCurriculum(division = class3eGenerate, subject = biologia, teacher = SzTo, numberOfActivities = 5)
        val curriculum3eChemia = createCurriculum(division = class3eGenerate, subject = chemia, teacher = ChAg, numberOfActivities = 5)
        val curriculum3eHistISpol = createCurriculum(division = class3eGenerate, subject = historiaISpoleczenstwo, teacher = DyLu, numberOfActivities = 2)
        val curriculum3eFizykaMedyczna = createCurriculum(division = class3eGenerate, subject = fizykaMedyczna, teacher = SzIr, numberOfActivities = 1)
        val curriculum3eMedycznyJezykAngielski = createCurriculum(division = class3eGenerate, subject = medycznyJezykAngielski, teacher = SzIr, numberOfActivities = 1)
        val curriculum3eReligia = createCurriculum(division = class3eGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)

        val curriculum3fJPolski = createCurriculum(division = class3fGenerate, subject = jPolski, teacher = GrRe, numberOfActivities = 4)
        val curriculum3f1grJAngielski = createCurriculum(division = class3fGenerate, subject = jAngielski, teacher = KoIr, numberOfActivities = 3)
        val curriculum3f2grJAngielski = createCurriculum(division = class3fGenerate, subject = jAngielski, teacher = BaEl, numberOfActivities = 3)
        val curriculum3fMatematyka = createCurriculum(division = class3fGenerate, subject = matematyka, teacher = JaMa, numberOfActivities = 6)
        val curriculum3fGrDz1WychowanieFizyczne = createCurriculum(division = class3fGenerate, subject = wychowanieFizyczne, teacher = ZaTo, numberOfActivities = 3)
        val curriculum3fGrDz2WychowanieFizyczne = createCurriculum(division = class3fGenerate, subject = wychowanieFizyczne, teacher = DaRe, numberOfActivities = 3)
        val curriculum3fGrChWychowanieFizyczne = createCurriculum(division = class3fGenerate, subject = wychowanieFizyczne, teacher = DaJa, numberOfActivities = 3)
        val curriculum3fGodzWych = createCurriculum(division = class3fGenerate, subject = godzWych, teacher = GuMa, numberOfActivities = 1)
        val curriculum3fBiologia = createCurriculum(division = class3fGenerate, subject = biologia, teacher = GuMa, numberOfActivities = 4)
        val curriculum3fChemia = createCurriculum(division = class3fGenerate, subject = chemia, teacher = SeMo, numberOfActivities = 4)
        val curriculum3fHistISpol = createCurriculum(division = class3fGenerate, subject = historiaISpoleczenstwo, teacher = LoEl, numberOfActivities = 2)
        val curriculum3fFizyka = createCurriculum(division = class3fGenerate, subject = fizyka, teacher = BlSł, numberOfActivities = 3) // Powina byc fizyka medyczna wedlug planu 2018/2019
        val curriculum3fReligia = createCurriculum(division = class3fGenerate, subject = religia, teacher = PePi, numberOfActivities = 2)


        curriculumListService.save(CurriculumListDTO(name = "test001", periodId = semestLetniPeriod.id, divisionOwnerId = lo2.id, curriculums = hashSetOf(
            curriculum1aJPolski,
//            curriculum1a1grJAngielski,
            curriculum1a2grJAngielski,
            curriculum1aGr1Jdodatkowy,
//            curriculum1aGr2Jdodatkowy,
//            curriculum1aGr3Jdodatkowy,
            curriculum1aWiedzaOKulturze,
            curriculum1aHistoria,
            curriculum1aWiedzaOSpołeczeństwie,
            curriculum1aPodstawyPrzedsiębiorczości,
            curriculum1aGeografia,
            curriculum1aBiologia,
            curriculum1aChemia,
            curriculum1aFizyka,
            curriculum1aMatematyka,
            curriculum1aInformatyka,
            curriculum1aGrDzWychowanieFizyczne,
            //curriculum1aGrChWychowanieFizyczne,
            curriculum1aEdukacjaDoBezpieczeństwa,
            curriculum1aGodzWych,
            curriculum1aWychowaniedoZyciaWRodzinie,
            curriculum1aReligia,

            curriculum1bJPolski,
//            curriculum1b1grJAngielski,
            curriculum1b2grJAngielski,
//            curriculum1bGr1Jdodatkowy,
//            curriculum1bGr2Jdodatkowy,
//            curriculum1bGr3Jdodatkowy,
            curriculum1bGr4Jdodatkowy,
            curriculum1bWiedzaOKulturze,
            curriculum1bHistoria,
            curriculum1bWiedzaOSpołeczeństwie,
            curriculum1bPodstawyPrzedsiębiorczości,
            curriculum1bGeografia,
            curriculum1bBiologia,
            curriculum1bChemia,
            curriculum1bFizyka,
            curriculum1bMatematyka,
            curriculum1bInformatyka,
//            curriculum1bGrDz1WychowanieFizyczne,
//            curriculum1bGrDz2WychowanieFizyczne,
            curriculum1bGrChWychowanieFizyczne,
            curriculum1bEdukacjaDoBezpieczeństwa,
            curriculum1bGodzWych,
            curriculum1bWychowaniedoZyciaWRodzinie,
            curriculum1bReligia,

            curriculum1cJPolski,
            curriculum1c1grJAngielski,
//            curriculum1c2grJAngielski,
            curriculum1cGr1Jdodatkowy,
//            curriculum1cGr2Jdodatkowy,
//            curriculum1cGr3Jdodatkowy,
            curriculum1cWiedzaOKulturze,
            curriculum1cHistoria,
            curriculum1cWiedzaOSpołeczeństwie,
            curriculum1cPodstawyPrzedsiębiorczości,
            curriculum1cGeografia,
            curriculum1cBiologia,
            curriculum1cChemia,
            curriculum1cFizyka,
            curriculum1cMatematyka,
            curriculum1cInformatyka,
            curriculum1cGrDz1WychowanieFizyczne,
//            curriculum1cGrDz2WychowanieFizyczne,
//            curriculum1cGrChWychowanieFizyczne,
            curriculum1cEdukacjaDoBezpieczeństwa,
            curriculum1cGodzWych,
            curriculum1cWychowaniedoZyciaWRodzinie,
            curriculum1cReligia,

            curriculum1dJPolski,
//            curriculum1d1grJAngielski,
            curriculum1d2grJAngielski,
            curriculum1dGr1Jdodatkowy,
//            curriculum1dGr2Jdodatkowy,
            curriculum1dWiedzaOKulturze,
            curriculum1dHistoria,
            curriculum1dWiedzaOSpołeczeństwie,
            curriculum1dPodstawyPrzedsiębiorczości,
            curriculum1dGeografia,
            curriculum1dBiologia,
            curriculum1dChemia,
            curriculum1dFizyka,
            curriculum1dMatematyka,
            curriculum1dInformatyka,
//            curriculum1dGrDz1WychowanieFizyczne,
            curriculum1dGrDz2WychowanieFizyczne,
//            curriculum1dGrChWychowanieFizyczne,
            curriculum1dEdukacjaDoBezpieczeństwa,
            curriculum1dGodzWych,
            curriculum1dWychowaniedoZyciaWRodzinie,
            curriculum1dReligia,

            curriculum1eJPolski,
//            curriculum1e1grJAngielski,
            curriculum1e2grJAngielski,
            curriculum1eGr1Jdodatkowy,
//            curriculum1eGr2Jdodatkowy,
//            curriculum1eGr3Jdodatkowy,
            curriculum1eWiedzaOKulturze,
            curriculum1eHistoria,
            curriculum1eWiedzaOSpołeczeństwie,
            curriculum1ePodstawyPrzedsiębiorczości,
            curriculum1eGeografia,
            curriculum1eBiologia,
            curriculum1eChemia,
            curriculum1eFizyka,
            curriculum1eMatematyka,
            curriculum1eInformatyka,
            curriculum1eGrDzWychowanieFizyczne,
//            curriculum1eGrChWychowanieFizyczne,
            curriculum1eEdukacjaDoBezpieczeństwa,
            curriculum1eGodzWych,
            curriculum1eWychowaniedoZyciaWRodzinie,
            curriculum1eReligia,

            curriculum1fJPolski,
            curriculum1f1grJAngielski,
//            curriculum1f2grJAngielski,
            curriculum1fGr1Jdodatkowy,
//            curriculum1fGr2Jdodatkowy,
//            curriculum1fGr3Jdodatkowy,
            curriculum1fWiedzaOKulturze,
            curriculum1fHistoria,
            curriculum1fWiedzaOSpołeczeństwie,
            curriculum1fPodstawyPrzedsiębiorczości,
            curriculum1fGeografia,
            curriculum1fBiologia,
            curriculum1fChemia,
            curriculum1fFizyka,
            curriculum1fMatematyka,
            curriculum1fInformatyka,
            curriculum1fGrDzWychowanieFizyczne,
//            curriculum1fGrChWychowanieFizyczne,
            curriculum1fEdukacjaDoBezpieczeństwa,
            curriculum1fGodzWych,
            curriculum1fWychowaniedoZyciaWRodzinie,
            curriculum1fReligia,

            curriculum2aJPolski,
            curriculum2a1grJAngielski,
//            curriculum2a2grJAngielski,
            curriculum2aGr1Jdodatkowy,
//            curriculum2aGr2Jdodatkowy,
//            curriculum2aGr3Jdodatkowy,
//            curriculum2aGr4Jdodatkowy,
            curriculum2aFizyka,
            curriculum2aMatematyka,
            curriculum2aInformatyka,
            curriculum2aGrDzWychowanieFizyczne,
//            curriculum2aGrChWychowanieFizyczne,
            curriculum2aGodzWych,
            curriculum2aHistISpol,
            curriculum2aTechAngielski,
            curriculum2aReligia,

            curriculum2bJPolski,
            curriculum2b1grJAngielski,
//            curriculum2b2grJAngielski,
            curriculum2bGr1Jdodatkowy,
//            curriculum2bGr2Jdodatkowy,
//            curriculum2bGr3Jdodatkowy,
//            curriculum2bGr4Jdodatkowy,
            curriculum2bGeografia,
            curriculum2bMatematyka,
            curriculum2bGrDzWychowanieFizyczne,
//            curriculum2bGrChWychowanieFizyczne,
            curriculum2bGodzWych,
            curriculum2bHistISpol,
            curriculum2bEkonomiaWPraktyce,
            curriculum2bReligia,

            curriculum2cJPolski,
//            curriculum2c1grJAngielski,
            curriculum2c2grJAngielski,
//            curriculum2cGr1Jdodatkowy,
//            curriculum2cGr2Jdodatkowy,
            curriculum2cGr3Jdodatkowy,
            curriculum2cMatematyka,
//            curriculum2cGrDz1WychowanieFizyczne,
//            curriculum2cGrDz2WychowanieFizyczne,
            curriculum2cGrChWychowanieFizyczne,
            curriculum2cGodzWych,
            curriculum2cHistoria,
            curriculum2cWiedzaOSpoleczenstwie,
            curriculum2cPrzyroda,
            curriculum2cLacinaPrawnicza,
            curriculum2cReligia,

            curriculum2dJPolski,
//            curriculum2d1grJAngielski,
            curriculum2d2grJAngielski,
//            curriculum2dGr1Jdodatkowy,
            curriculum2dGr2Jdodatkowy,
            curriculum2dMatematyka,
            curriculum2dGrDzWychowanieFizyczne,
//            curriculum2dGrChWychowanieFizyczne,
            curriculum2dGodzWych,
            curriculum2dHistoria,
            curriculum2dPrzyroda,
            curriculum2dEdukacjaDziennikarska,
            curriculum2dLiteraturaObca,
            curriculum2dReligia,

            curriculum2eJPolski,
            curriculum2e1grJAngielski,
//            curriculum2e2grJAngielski,
            curriculum2eGr1Jdodatkowy,
//            curriculum2eGr2Jdodatkowy,
//            curriculum2eGr3Jdodatkowy,
            curriculum2eMatematyka,
            curriculum2eGrDz1WychowanieFizyczne,
//            curriculum2eGrDz2WychowanieFizyczne,
//            curriculum2eGrChWychowanieFizyczne,
            curriculum2eGodzWych,
            curriculum2eBiologia,
            curriculum2eChemia,
            curriculum2eHistISpol,
            curriculum2eFizykaMedyczna,
            curriculum2eReligia,

            curriculum2fJPolski,
            curriculum2f1grJAngielski,
//            curriculum2f2grJAngielski,
            curriculum2fGr1Jdodatkowy,
//            curriculum2fGr2Jdodatkowy,
//            curriculum2fGr3Jdodatkowy,
            curriculum2fMatematyka,
//            curriculum2fGrDz1WychowanieFizyczne,
//            curriculum2fGrDz2WychowanieFizyczne,
            curriculum2fGrChWychowanieFizyczne,
            curriculum2fGodzWych,
            curriculum2fBiologia,
            curriculum2fChemia,
            curriculum2fHistISpol,
            curriculum2fReligia,

            curriculum3aJPolski,
            curriculum3a1grJAngielski,
//            curriculum3a2grJAngielski,
            curriculum3aFizyka,
            curriculum3aMatematyka,
            curriculum3aInformatyka,
            curriculum3aGrDzWychowanieFizyczne,
//            curriculum3aGrChWychowanieFizyczne,
            curriculum3aGodzWych,
            curriculum3aHistISpol,
            curriculum3aTechAngielski,
            curriculum3aReligia,

            curriculum3bJPolski,
            curriculum3b1grJAngielski,
//            curriculum3b2grJAngielski,
            curriculum3bGeografia,
            curriculum3bMatematyka,
            curriculum3bGrDz1WychowanieFizyczne,
//            curriculum3bGrDz2WychowanieFizyczne,
//            curriculum3bGrChWychowanieFizyczne,
            curriculum3bGodzWych,
            curriculum3bHistISpol,
            curriculum3bReligia,

            curriculum3cJPolski,
            curriculum3c1grJAngielski,
//            curriculum3c2grJAngielski,
            curriculum3cMatematyka,
            curriculum3cGrDzWychowanieFizyczne,
//            curriculum3cGrChWychowanieFizyczne,
            curriculum3cGodzWych,
            curriculum3cHistoria,
            curriculum3cWiedzaOSpoleczenstwie,
            curriculum3cPrzyroda,
            curriculum3cReligia,

            curriculum3dJPolski,
            curriculum3d1grJAngielski,
//            curriculum3d2grJAngielski,
            curriculum3dGr1Jdodatkowy,
//            curriculum3dGr2Jdodatkowy,
            curriculum3dMatematyka,
//            curriculum3dGr1DzWychowanieFizyczne,
//            curriculum3dGr2DzWychowanieFizyczne,
            curriculum3dGrChWychowanieFizyczne,
            curriculum3dGodzWych,
            curriculum3dHistoria,
            curriculum3dPrzyroda,
            curriculum3dEdukacjaDziennikarska,
            curriculum3dLiteraturaObca,
            curriculum3dReligia,

            curriculum3eJPolski,
            curriculum3e1grJAngielski,
//            curriculum3e2grJAngielski,
            curriculum3eMatematyka,
            curriculum3eGrDz1WychowanieFizyczne,
//            curriculum3eGrDz2WychowanieFizyczne,
//            curriculum3eGrChWychowanieFizyczne,
            curriculum3eGodzWych,
            curriculum3eBiologia,
            curriculum3eChemia,
            curriculum3eHistISpol,
            curriculum3eFizykaMedyczna,
            curriculum3eMedycznyJezykAngielski,
            curriculum3eReligia,

            curriculum3fJPolski,
            curriculum3f1grJAngielski,
//            curriculum3f2grJAngielski,
            curriculum3fMatematyka,
//            curriculum3fGrDz1WychowanieFizyczne,
//            curriculum3fGrDz2WychowanieFizyczne,
            curriculum3fGrChWychowanieFizyczne,
            curriculum3fGodzWych,
            curriculum3fBiologia,
            curriculum3fChemia,
            curriculum3fHistISpol,
            curriculum3fFizyka,
            curriculum3fReligia
        )))


        // =====================================================
        // Preference Teacher
        // =====================================================
        DeWi.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = -10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = 5)
            )
        }
        DeWi = teacherService.save(DeWi)

        JaEd.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = 5)
            )
        }
        JaEd = teacherService.save(JaEd)

        StBo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0)
            )
        }
        StBo = teacherService.save(StBo)

        urbanek.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0)
            )
        }
        urbanek = teacherService.save(urbanek)

        gierlach.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10)
            )
        }
        gierlach = teacherService.save(gierlach)

        KlLu.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10)
            )
        }
        KlLu = teacherService.save(KlLu)

        PeRe.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = literaturaObca.id, points = 10)
            )
        }
        PeRe = teacherService.save(PeRe)

        GrRe.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10)
            )
        }
        GrRe = teacherService.save(GrRe)

        PrAr.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jPolski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDziennikarska.id, points = 10)
            )
        }
        PrAr = teacherService.save(PrAr)

        DyLu.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historiaISpoleczenstwo.id, points = 10)
            )
        }
        DyLu = teacherService.save(DyLu)

        LoEl.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historiaISpoleczenstwo.id, points = 10)

            )
        }
        LoEl = teacherService.save(LoEl)

        SwEl.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = historiaISpoleczenstwo.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = 10)
            )
        }
        SwEl = teacherService.save(SwEl)

        BaEl.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10)
            )
        }
        BaEl = teacherService.save(BaEl)

        KaMo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10)
            )
        }
        KaMo = teacherService.save(KaMo)

        KaTo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10)
            )
        }
        KaTo = teacherService.save(KaTo)

        KiBe.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10)
            )
        }
        KiBe = teacherService.save(KiBe)

        KoIr.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10)
            )
        }
        KoIr = teacherService.save(KoIr)

        TrAg.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jAngielski.id, points = 10)
            )
        }
        TrAg = teacherService.save(TrAg)

        CzBe.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = 10)
            )
        }
        CzBe = teacherService.save(CzBe)

        JaMa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = 5)
            )
        }
        JaMa = teacherService.save(JaMa)

        MiJo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = 10)
            )
        }
        MiJo = teacherService.save(MiJo)

        HaAn.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = 10)
            )
        }
        HaAn = teacherService.save(HaAn)

        PrAn.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = 10)
            )
        }
        PrAn = teacherService.save(PrAn)

        PrKa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jNiemiecki.id, points = 10)
            )
        }
        PrKa = teacherService.save(PrKa)

        RaBe.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jFrancuski.id, points = 10)
            )
        }
        RaBe = teacherService.save(RaBe)

        ChAg.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = 10)
            )
        }
        ChAg = teacherService.save(ChAg)

        SeMo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = chemia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = przyroda.id, points = 10)
            )
        }
        SeMo = teacherService.save(SeMo)

        GoTa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = geografia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = przyroda.id, points = 10)
            )
        }
        GoTa = teacherService.save(GoTa)

        GuMa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = 10)
            )
        }
        GuMa = teacherService.save(GuMa)

        SzTo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = biologia.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = przyroda.id, points = 10)
            )
        }
        SzTo = teacherService.save(SzTo)

        DaJa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10)
            )
        }
        DaJa = teacherService.save(DaJa)

        WiWo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10)
            )
        }
        WiWo = teacherService.save(WiWo)

        ZaTo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10)
            )
        }
        ZaTo = teacherService.save(ZaTo)

        DaRe.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10)
            )
        }
        DaRe = teacherService.save(DaRe)

        RaMa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowanieFizyczne.id, points = 10)
            )
        }
        RaMa = teacherService.save(RaMa)

        BlSł.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = 5),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = przyroda.id, points = 10)

            )
        }
        BlSł = teacherService.save(BlSł)

        JóSt.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = 5),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = matematyka.id, points = 5)
            )
        }
        JóSt = teacherService.save(JóSt)

        SzIr.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizyka.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = techAngielski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = medycznyJezykAngielski.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = fizykaMedyczna.id, points = 10)
            )
        }
        SzIr = teacherService.save(SzIr)

        DzMa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = 10)
            )
        }
        DzMa = teacherService.save(DzMa)

        SoRy.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = informatyka.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = 5))
        }
        SoRy = teacherService.save(SoRy)

        KuKa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jLacińskiPrawniczy.id, points = 10)
            )
        }
        KuKa = teacherService.save(KuKa)

        PePi.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = 10)
            )
        }
        PePi = teacherService.save(PePi)

        PiTa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = religia.id, points = 10)
            )
        }
        PiTa = teacherService.save(PiTa)

        TwMa.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = wiedzaOKulturze.id, points = 10)
            )
        }
        TwMa = teacherService.save(TwMa)

        SuZb.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = ekonomiaWPraktyce.id, points = 10)

            )
        }
        SuZb = teacherService.save(SuZb)

        SM.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = 10),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = ekonomiaWPraktyce.id, points = 10)
            )
        }
        SM = teacherService.save(SM)

        ŁoDo.apply {
            preferenceSubjectByTeacher = hashSetOf(
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = godzWych.id, points = 0),
                PreferenceSubjectByTeacherDTO(teacherId = this.id, subjectId = jRosyjski.id, points = 10)
            )
        }
        ŁoDo = teacherService.save(ŁoDo)


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
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
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
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
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
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
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
                PreferenceSubjectByPlaceDTO(placeId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
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
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = CzBe.id, points = 5)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1a = divisionService.save(class1a)


        class1aGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = DzMa.id, points = 5)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1aGenerate = divisionService.save(class1aGenerate)

        class1bGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = DyLu.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1bGenerate = divisionService.save(class1bGenerate)

        class1cGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = LoEl.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1cGenerate = divisionService.save(class1cGenerate)

        class1dGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = SwEl.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1dGenerate = divisionService.save(class1dGenerate)

        class1eGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = KiBe.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1eGenerate = divisionService.save(class1eGenerate)

        class1fGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = SeMo.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class1fGenerate = divisionService.save(class1fGenerate)

        div1aBoyWychowanieFizyczne.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1aBoyWychowanieFizyczne = divisionService.save(div1aBoyWychowanieFizyczne)

        div1aGirlWychowanieFizyczne.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1aGirlWychowanieFizyczne = divisionService.save(div1aGirlWychowanieFizyczne)


        div1aGrupa1.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1aGrupa1 = divisionService.save(div1aGrupa1)

        div1aGrupa2.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1aGrupa2 = divisionService.save(div1aGrupa2)



        div1Gr1Niemiecki1.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1Gr1Niemiecki1 = divisionService.save(div1Gr1Niemiecki1)


        div1Gr1Niemiecki2.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1Gr1Niemiecki2 = divisionService.save(div1Gr1Niemiecki2)


        div1Gr1Niemiecki3.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1Gr1Niemiecki3 = divisionService.save(div1Gr1Niemiecki3)


        div1Gr1Rosyjski1.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1Gr1Rosyjski1 = divisionService.save(div1Gr1Rosyjski1)


        div1Gr1Rosyjski2.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1Gr1Rosyjski2 = divisionService.save(div1Gr1Rosyjski2)


        div1Gr1Francuski1.apply {
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        div1Gr1Francuski1 = divisionService.save(div1Gr1Francuski1)


        class2aGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)

            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = CzBe.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class2aGenerate = divisionService.save(class2aGenerate)

        class2bGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)

            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = KlLu.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        class2bGenerate = divisionService.save(class2bGenerate)

        class2cGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)

            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = BlSł.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class2cGenerate = divisionService.save(class2cGenerate)

        class2dGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)

            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = PeRe.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        class2dGenerate = divisionService.save(class2dGenerate)

        class2eGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = JaMa.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class2eGenerate = divisionService.save(class2eGenerate)

        class2fGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)

            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = ChAg.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class2fGenerate = divisionService.save(class2fGenerate)



        class3aGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = JaMa.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class3aGenerate = divisionService.save(class3aGenerate)

        class3bGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = GoTa.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        class3bGenerate = divisionService.save(class3bGenerate)

        class3cGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)

            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = WiWo.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class3cGenerate = divisionService.save(class3cGenerate)

        class3dGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = biologia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = chemia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historiaISpoleczenstwo.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizykaMedyczna.id, points = -10_000)

            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = PrAr.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()
        }
        class3dGenerate = divisionService.save(class3dGenerate)

        class3eGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = fizyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = SzTo.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class3eGenerate = divisionService.save(class3eGenerate)

        class3fGenerate.apply {
            preferencesSubjectByDivision = hashSetOf(
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = historia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOSpołeczeństwie.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wiedzaOKulturze.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = podstawyPrzedsiębiorczości.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = informatyka.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = geografia.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jNiemiecki.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jFrancuski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jRosyjski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDoBezpieczeństwa.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = wychowaniedoZyciaWRodzinie.id, points = -10_000),

                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = jLacińskiPrawniczy.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = techAngielski.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = ekonomiaWPraktyce.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = przyroda.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = literaturaObca.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = edukacjaDziennikarska.id, points = -10_000),
                PreferenceSubjectByDivisionDTO(divisionId = this.id, subjectId = medycznyJezykAngielski.id, points = -10_000)
            )
            preferencesTeacherByDivision = hashSetOf(
                PreferenceTeacherByDivisionDTO(divisionId = this.id, teacherId = GuMa.id, points = 10)
            )
            preferencesDataTimeForDivision = calculateTypicalPreferenceForTime()

        }
        class3fGenerate = divisionService.save(class3fGenerate)


        userService.createUser(
            login = "2lo",
            password = "pass",
            firstName = null,
            lastName = null,
            email = "2lo@gmail.com",
            imageUrl = null,
            langKey = "en",
            schoolId = lo2.id,
            teacherId = null
        )

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

    private fun createDivision(name: String, shortName: String, divisionType: DivisionType, numberOfPeople: Long, parents: Set<DivisionDTO> = emptySet(), schoolId: Long? = null): DivisionDTO {
        val division = DivisionDTO(name = name, shortName = shortName, divisionType = divisionType, numberOfPeople = numberOfPeople, parents = parents, divisionOwnerId = schoolId)
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
