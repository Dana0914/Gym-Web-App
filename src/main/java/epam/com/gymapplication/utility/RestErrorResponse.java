package epam.com.gymapplication.utility;

import java.time.LocalDateTime;

public record RestErrorResponse(int status, String message, LocalDateTime timestamp) {}
