package com.patres.timetable.service

import com.patres.timetable.TimetableApp
import com.patres.timetable.config.Constants
import com.patres.timetable.domain.User
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.service.util.RandomUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
@Transactional
open class UserServiceIntTest {

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val userService: UserService? = null

    @Test
    open fun assertThatUserMustExistToResetPassword() {
        var maybeUser: User? = userService!!.requestPasswordReset("john.doe@localhost")
        assertThat(maybeUser != null).isFalse()

        maybeUser = userService.requestPasswordReset("admin@localhost")
        assertThat(maybeUser != null).isTrue()

        assertThat(maybeUser!!.email).isEqualTo("admin@localhost")
        assertThat(maybeUser.resetDate).isNotNull()
        assertThat(maybeUser.resetKey).isNotNull()
    }

    @Test
    open fun assertThatOnlyActivatedUserCanRequestPasswordReset() {
        val user = userService!!.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US")
        val maybeUser = userService.requestPasswordReset("john.doe@localhost")
        assertThat(maybeUser == null)
        userRepository!!.delete(user)
    }

    @Test
    open fun assertThatResetKeyMustNotBeOlderThan24Hours() {
        val user = userService!!.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US")

        val daysAgo = Instant.now().minus(25, ChronoUnit.HOURS)
        val resetKey = RandomUtil.generateResetKey()
        user.activated = true
        user.resetDate = daysAgo
        user.resetKey = resetKey

        userRepository!!.save(user)

        val maybeUser = userService.completePasswordReset("johndoe2", user.resetKey!!)

        assertThat(maybeUser == null)

        userRepository.delete(user)
    }

    @Test
    open fun assertThatResetKeyMustBeValid() {
        val user = userService!!.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US")

        val daysAgo = Instant.now().minus(25, ChronoUnit.HOURS)
        user.activated = true
        user.resetDate = daysAgo
        user.resetKey = "1234"
        userRepository!!.save(user)
        val maybeUser = userService.completePasswordReset("johndoe2", user.resetKey!!)
        assertThat(maybeUser == null)
        userRepository.delete(user)
    }

    @Test
    open fun assertThatUserCanResetPassword() {
        val user = userService!!.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US")
        val oldPassword = user.password
        val daysAgo = Instant.now().minus(2, ChronoUnit.HOURS)
        val resetKey = RandomUtil.generateResetKey()
        user.activated = true
        user.resetDate = daysAgo
        user.resetKey = resetKey
        userRepository!!.save(user)
        val maybeUser = userService.completePasswordReset("johndoe2", user.resetKey!!)
        assertThat(maybeUser != null).isTrue()
        assertThat(maybeUser!!.resetDate).isNull()
        assertThat(maybeUser.resetKey).isNull()
        assertThat(maybeUser.password).isNotEqualTo(oldPassword)

        userRepository.delete(user)
    }

    @Test
    open fun testFindNotActivatedUsersByCreationDateBefore() {
        userService!!.removeNotActivatedUsers()
        val now = Instant.now()
        val users = userRepository!!.findAllByActivatedIsFalseAndCreatedDateBefore(now.minus(3, ChronoUnit.DAYS))
        assertThat(users).isEmpty()
    }

    @Test
    open fun assertThatAnonymousUserIsNotGet() {
        val pageable = PageRequest(0, userRepository!!.count().toInt())
        val allManagedUsers = userService!!.getAllManagedUsers(pageable)
        assertThat(allManagedUsers.content.stream()
            .noneMatch { user -> Constants.ANONYMOUS_USER == user.login })
            .isTrue()
    }

    @Test
    open fun testRemoveNotActivatedUsers() {
        val user = userService!!.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US")
        user.activated = false
        user.createdDate = Instant.now().minus(30, ChronoUnit.DAYS)
        userRepository!!.save(user)
        assertThat(userRepository.findOneByLogin("johndoe") != null)
        userService.removeNotActivatedUsers()
        assertThat(userRepository.findOneByLogin("johndoe") == null)
    }
}
