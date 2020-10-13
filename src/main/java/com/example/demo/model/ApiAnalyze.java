package com.example.demo.model;

import java.util.Map;

public class ApiAnalyze {
    private Map<String, Pro> project;

    public static class Pro {
        private Long _count;
        Map<String, Object> theme;

        public Pro() {
        }

        public Long get_count() {
            return _count;
        }

        public void set_count(Long _count) {
            this._count = _count;
        }

        public Map<String, Object> getTheme() {
            return theme;
        }

        public void setTheme(Map<String, Object> theme) {
            this.theme = theme;
        }
    }

    public Map<String, Pro> getProject() {
        return project;
    }

    public void setProject(Map<String, Pro> project) {
        this.project = project;
    }
}
