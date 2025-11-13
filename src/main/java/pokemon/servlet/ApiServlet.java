package pokemon.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pokemon.api.GetTrainer;
import pokemon.api.PutTrainer;
import pokemon.controller.AvatarController;
import pokemon.controller.TrainerController;
import pokemon.model.Avatar;

@WebServlet(name = "ApiServlet", urlPatterns = {"/api/*"})
@MultipartConfig(maxFileSize = 16 * 1024 * 1024)
public class ApiServlet extends HttpServlet {
    private TrainerController trainerController;
    private AvatarController avatarController;
    private final Jsonb jsonb = JsonbBuilder.create();

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile(
                "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
        );
        public static final Pattern TRAINERS = Pattern.compile("/trainers/?");
        public static final Pattern TRAINER = Pattern.compile("/trainers/(%s)/?".formatted(UUID.pattern()));
        public static final Pattern AVATAR = Pattern.compile("/trainers/(%s)/avatar/?".formatted(UUID.pattern()));
    }

    @Inject
    public ApiServlet(TrainerController trainerController, AvatarController avatarController) {
        this.trainerController = trainerController;
        this.avatarController = avatarController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getRequestPath(request);
        if (path.matches(Patterns.TRAINERS.pattern())) {
            response.setContentType("application/json");
            response.getWriter().write(jsonb.toJson(trainerController.getTrainers()));
        } else if (path.matches(Patterns.TRAINER.pattern())) {
            response.setContentType("application/json");
            UUID uuid = parseUuid(Patterns.TRAINER, path);
            GetTrainer trainer = trainerController.getTrainer(uuid);
            if (trainer == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            response.getWriter().write(jsonb.toJson(trainer));
        } else if (path.matches(Patterns.AVATAR.pattern())) {
            response.setContentType("image/png");
            UUID uuid = parseUuid(Patterns.AVATAR, path);
            Optional<Avatar> avatar = avatarController.getAvatar(uuid);
            if (avatar.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            byte[] data = avatar.get().getData();
            response.setContentLength(data.length);
            response.getOutputStream().write(data);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getRequestPath(request);
        if (path.matches(Patterns.TRAINER.pattern())) {
            UUID uuid = parseUuid(Patterns.TRAINER, path);
            trainerController.putTrainer(uuid, jsonb.fromJson(request.getReader(), PutTrainer.class));
            response.addHeader("Location", "/api/trainers/" + uuid);
        } else if (path.matches(Patterns.AVATAR.pattern())) {
            UUID uuid = parseUuid(Patterns.AVATAR, path);
            avatarController.putAvatar(uuid, request.getPart("avatar").getInputStream());
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getRequestPath(request);
        if (path.matches(Patterns.AVATAR.pattern())) {
            UUID uuid = parseUuid(Patterns.AVATAR, path);
            avatarController.deleteAvatar(uuid);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String getRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    private static UUID parseUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("UUID not found in path: " + path);
    }
}
