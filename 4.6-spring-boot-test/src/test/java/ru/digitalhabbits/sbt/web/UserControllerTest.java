package ru.digitalhabbits.sbt.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.digitalhabbits.sbt.model.CreateUserRequest;
import ru.digitalhabbits.sbt.model.UserInfoResponse;
import ru.digitalhabbits.sbt.service.UserService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.digitalhabbits.sbt.utils.UserHelper.buildCreateUserRequest;
import static ru.digitalhabbits.sbt.utils.UserHelper.buildUserInfoResponse;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureRestDocs
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final Gson gson = new GsonBuilder().create();

    @Test
    void users()
            throws Exception {
        final UserInfoResponse response = buildUserInfoResponse();
        when(userService.findAllUsers()).thenReturn(List.of(response));

        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$[0].middleName").value(response.getMiddleName()))
                .andExpect(jsonPath("$[0].lastName").value(response.getLastName()))
                .andExpect(jsonPath("$[0].age").value(response.getAge()))
        .andDo(document("users",
                responseFields(
                        fieldWithPath("[].id").description("ID"),
                        fieldWithPath("[].firstName").description("First Name"),
                        fieldWithPath("[].middleName").description("Middle Name"),
                        fieldWithPath("[].lastName").description("Last Name"),
                        fieldWithPath("[].age").description("Age")
                )));
    }

    @Test
    void createUser()
            throws Exception {
        final CreateUserRequest request = buildCreateUserRequest();
        final UserInfoResponse response = buildUserInfoResponse(request);
        when(userService.createUser(request)).thenReturn(response);

        mockMvc.perform(post("/users")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.middleName").value(response.getMiddleName()))
                .andExpect(jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.age").value(response.getAge()))
                .andDo(document("createUser",
                        requestFields(
                                fieldWithPath("firstName").description("First Name"),
                                fieldWithPath("middleName").description("Middle Name"),
                                fieldWithPath("lastName").description("Last Name"),
                                fieldWithPath("age").description("Age")
                        ),
                        responseFields(
                                fieldWithPath("id").description("ID"),
                                fieldWithPath("firstName").description("First Name"),
                                fieldWithPath("middleName").description("Middle Name"),
                                fieldWithPath("lastName").description("Last Name"),
                                fieldWithPath("age").description("Age")
                        )));;
    }
}