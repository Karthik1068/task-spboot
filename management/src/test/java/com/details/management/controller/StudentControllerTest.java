package com.details.management.controller;

import com.details.management.ManagementApplication;
import com.details.management.config.JwtTokenUtil;
import com.details.management.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ManagementApplication.class)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    protected StudentController studentController;

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    Student student;
    Integer studentId;

    //DATA
    String student1 = "{\n" +
            "  \"studentID\": 1,\n" +
            "  \"firstName\": \"Kumar\",\n" +
            "  \"lastName\": \"Murugan\",\n" +
            "  \"phone\": 9844554455\n" +
            "}";
    String student2 = "{\n" +
            "  \"studentID\": 2,\n" +
            "  \"firstName\": \"Thirumurthy\",\n" +
            "  \"lastName\": \"Selva\",\n" +
            "  \"phone\": 9095065068\n" +
            "}";

    String instructor1 = "{\n" +
            "  \"instructorID\": 3,\n" +
            "  \"headedBy\": \"Karthikeyan\",\n" +
            "  \"firstName\": \"Prabu\",\n" +
            "  \"lastName\": \"Manickam\",\n" +
            "  \"phone\": \"7502008800\",\n" +
            "  \"department\": {\n" +
            "    \"name\": \"Computer Science\",\n" +
            "    \"location\": \"Chennai\"\n" +
            "  }\n" +
            "}";

    String instructor2 = "{\n" +
            "      \"instructorID\": 4,\n" +
            "      \"headedBy\": \"Raja\",\n" +
            "      \"firstName\": \"Karthikeyan\",\n" +
            "      \"lastName\": \"Manickam\",\n" +
            "      \"phone\": \"7502038800\",\n" +
            "      \"department\": {\n" +
            "        \"name\": \"Maths\",\n" +
            "        \"location\": \"Chennai\"\n" +
            "      }\n" +
            "    }";

    String course1 = "{\n" +
            "    \"courseID\": 5,\n" +
            "    \"duration\": 10,\n" +
            "    \"name\": \"Scheduling\",\n" +
            "    \"instructor\": "+instructor1+",\n" +
            "    \"department\": {\n" +
            "      \"name\": \"Computer Science\",\n" +
            "      \"location\": \"Chennai\"\n" +
            "    }\n" +
            "  }";

    String course2 = "{\n" +
            "    \"courseID\": 6,\n" +
            "    \"duration\": 20,\n" +
            "    \"name\": \"Teaching\",\n" +
            "    \"instructor\": "+instructor2+",\n" +
            "    \"department\": {\n" +
            "      \"name\": \"English\",\n" +
            "      \"location\": \"Chennai\"\n" +
            "    }\n" +
            "  }";

    String course3 = "{\n" +
            "    \"courseID\": 7,\n" +
            "    \"duration\": 40,\n" +
            "    \"name\": \"Operating Systems\",\n" +
            "    \"instructor\": "+instructor1+",\n" +
            "    \"department\": {\n" +
            "      \"name\": \"Cs\",\n" +
            "      \"location\": \"Chennai\"\n" +
            "    }\n" +
            "  }";

    String course4 = "{\n" +
            "    \"courseID\": 8,\n" +
            "    \"duration\": 50,\n" +
            "    \"name\": \"Testing\",\n" +
            "    \"instructor\": "+instructor2+",\n" +
            "    \"department\": {\n" +
            "      \"name\": \"CS\",\n" +
            "      \"location\": \"Chennai\"\n" +
            "    }\n" +
            "  }";

    String jwtToken;

    @Before
    public void setUp() throws Exception {
        //this.mvc = standaloneSetup(this.studentController).build();
        jwtToken  = mvc.perform( MockMvcRequestBuilders
                        .post("/authenticate")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"password\": \"welcome\",\n" +
                                "  \"username\": \"testuser\"\n" +
                                "}")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString()
                .replace("{\"token\":\"", "")
                .replace("\"}", "");

        setupStudents();
        setupInstructors();
        setupCourses();
        enrollCourses();
    }

    @Test
    public void test_assigned_courses_against_the_student() throws Exception {

        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/academy/students/1/courses")
                .header("Authorization", "Bearer "+jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/academy/students/2/courses")
                .header("Authorization", "Bearer "+jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void test_students_under_instructor() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/academy/students?instructorId=3")
                .header("Authorization", "Bearer "+jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/academy/students?instructorId=4")
                .header("Authorization", "Bearer "+jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void test_total_total_duration_for_students() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/academy/students/1/courses/duration")
                .header("Authorization", "Bearer "+jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Total Course Duration = 100"));

        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/academy/students/2/courses/duration")
                .header("Authorization", "Bearer "+jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Total Course Duration = 70"));
    }


    private void setupStudents() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/academy/students")
                .header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(student1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Kumaresan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Murugan"));

        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/academy/students")
                .header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(student2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Thirumurthy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Selvaraj"));
    }

    public void setupInstructors() throws Exception{
        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/academy/instructors")
                .header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(instructor1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Prabu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Manickam"));

        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/academy/instructors")
                .header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(instructor2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Karthikeyan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Manickam"));
    }

    public void setupCourses() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/academy/courses")
                .header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(course1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Scheduling"));

        mvc.perform( MockMvcRequestBuilders
                    .post("/api/v1/academy/courses")
                    .header("Authorization", "Bearer "+jwtToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(course2))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("AutoCAD designing"));

        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/academy/courses")
                .header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(course3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Operating Systems"));

        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/academy/courses")
                .header("Authorization", "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(course4))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Surveying"));

    }

        private void enrollCourses() throws Exception {
            //Enroll courses
            mvc.perform( MockMvcRequestBuilders
                    .post("/api/v1/academy/students/1/courses/5")
                    .header("Authorization", "Bearer "+jwtToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(course4))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Course :5 has been added to Student: 1 successfully"));

            mvc.perform( MockMvcRequestBuilders
                    .post("/api/v1/academy/students/1/courses/7")
                    .header("Authorization", "Bearer "+jwtToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(course4))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Course :7 has been added to Student: 1 successfully"));

            mvc.perform( MockMvcRequestBuilders
                    .post("/api/v1/academy/students/2/courses/6")
                    .header("Authorization", "Bearer "+jwtToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(course4))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Course :6 has been added to Student: 2 successfully"));

            mvc.perform( MockMvcRequestBuilders
                    .post("/api/v1/academy/students/2/courses/8")
                    .header("Authorization", "Bearer "+jwtToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(course4))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Course :8 has been added to Student: 2 successfully"));

            mvc.perform( MockMvcRequestBuilders
                    .post("/api/v1/academy/students/1/courses/8")
                    .header("Authorization", "Bearer "+jwtToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(course4))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Course :8 has been added to Student: 1 successfully"));

        }
}
