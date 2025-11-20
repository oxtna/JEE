package pokemon.dto;

import java.util.List;
import java.util.UUID;

public class GetRegions {
    public static class Region {
        private UUID id;
        private String name;

        public Region() {}

        public Region(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private List<Region> regions;

    public GetRegions() {}

    public GetRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
