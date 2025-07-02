public class CategoryUtil {
    public static String getCategoryName(int categoryId) {
        return switch (categoryId) {
            case 1 -> "athleisure";
            case 2 -> "grunge";
            case 3 -> "hippie";
            case 4 -> "military";
            case 5 -> "normcore";
            case 6 -> "preppy";
            case 7 -> "street";
            case 8 -> "minimal";
            default -> throw new IllegalArgumentException("Invalid category ID: " + categoryId);
        };
    }
} 