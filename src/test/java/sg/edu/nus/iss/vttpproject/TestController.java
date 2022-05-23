package sg.edu.nus.iss.vttpproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.vttpproject.repository.UserRepository;
import sg.edu.nus.iss.vttpproject.services.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class TestController {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService uSvc;

    // Home Controller
    @Test
    public void homeShouldReturn200() {

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void homeSearchNewsShouldReturn200() {

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/searchnews")
                .queryParam("search", "curry")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    // Login Controller
    @Test
    public void signUpShouldReturn200() throws Exception {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "test");
        form.add("password", "test");

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.post("/signup")
                .params(form)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("Sign up successfully!"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @AfterEach
    public void delete() {
        uSvc.deleteUsername("test");
    }

    @Test
    public void signUpShouldFail() throws Exception {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "fred");
        form.add("password", "fred");

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.post("/signup")
                .params(form)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("Username taken!"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void shouldNotAuthenticate() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "invalid");
        form.add("password", "invalid");
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.post("/authenticate")
                .params(form)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("Login Invalid!"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void shouldAuthenticate() throws Exception {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "fred");
        form.add("password", "fred");
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.post("/authenticate")
                .params(form)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        mvc.perform(req).andDo(print()).andExpect(view().name("redirect:/"));
    }

    // Player Controller
    @Test
    public void playerShouldReturn200() {

        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/players");

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void playerStatsShouldReturn200() {
        MockHttpSession sess = new MockHttpSession();
        sess.setAttribute("username", "fred");
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/players/stats/203937")
                .session(sess);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("Kyle Anderson"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void playerStatsWithoutUsername() {
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/players/stats/203937");

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("Kyle Anderson"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void playerAddtoFavShouldReturn200() {

        MockHttpSession sess = new MockHttpSession();
        sess.setAttribute("username", "fred");
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/players/addtofav/203937")
                .session(sess);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("Kyle Anderson"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void ShouldLogOut() {

        MockHttpSession sess = new MockHttpSession();
        sess.setAttribute("username", "test");
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/logout")
                .session(sess);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        // MockHttpServletResponse resp = result.getResponse();
        // try {
        // System.out.print(">>>>SESS: " + sess.getAttribute("username").toString());
        // String payload = sess.getAttribute("username").toString();
        // } catch (Exception ex) {
        // assertTrue(true);
        // return;
        // }
        assertTrue(sess.isInvalid());
    }

    // Team Controller
    @Test
    public void getTeamsShouldReturn200() {
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/teams")
                .accept(MediaType.TEXT_HTML_VALUE);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void teamStatsShouldReturn200() {
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/teams/stats/1");

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("<span class=\"playername\">Atlanta Hawks</span><br>"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void teamStatsUserNotNull() {
        String username = "fred";
        MockHttpSession sess = new MockHttpSession();
        sess.setAttribute("username", username);
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/teams/stats/1")
        .session(sess);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("<span class=\"playername\">Atlanta Hawks</span><br>"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void teamAddtoFavShouldReturnToLogin() {

        RequestBuilder req = MockMvcRequestBuilders.get("/teams/addtofav/1");

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertFalse(payload.contains("<span class=\"playername\">Atlanta Hawks</span><br>"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    @Test
    public void teamAddtoFavShouldaddtofav() {
        String username = "fred";
        MockHttpSession sess = new MockHttpSession();
        sess.setAttribute("username", username);
        RequestBuilder req = MockMvcRequestBuilders.get("/teams/addtofav/1")
                .session(sess);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertTrue(payload.contains("<span class=\"playername\">Atlanta Hawks</span><br>"));
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

    // User Controller
    @Test
    public void userfavShouldReturn200() {
        String username = "fred";
        MockHttpSession sess = new MockHttpSession();
        sess.setAttribute("username", username);
        // Build the request
        RequestBuilder req = MockMvcRequestBuilders.get("/user/fred/favourites")
                // .pathInfo("/" + username)
                // .pathInfo("/favourites")
                .session(sess);

        // Call the controller
        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("cannot perform mvc invocation", ex);
            return;
        }

        // Get response
        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("cannot retrieve response payload", ex);
            return;
        }
    }

}
