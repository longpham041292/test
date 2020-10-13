package com.example.demo.service;

import com.example.demo.model.ApiAnalyze;
import com.example.demo.model.Method;
import com.example.demo.model.Project;
import com.example.demo.model.Theme;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogsAnalyzeService {

    public ApiAnalyze logAnalyze(String logs) {
        int n = 0;
        List<String> log = new Gson().fromJson(logs, List.class);
        List<Project> projectList = new ArrayList<>();
        List<String> projectName = new ArrayList<>();
        Map<Integer, List<String>> apiMap = new HashMap<>();
        for (String apis : log) {
            n++;
            List<String> construct = Arrays.asList(apis.split("/"));
            apiMap.put(n, construct);
            projectName.add(construct.get(0));
        }
        List<String> projectDistinct = projectName.stream().distinct().collect(Collectors.toList());
        projectDistinct.forEach(p -> {
            Project project = new Project();
            project.setName(p);
            Long count = apiMap.values().stream().filter(l -> p.equalsIgnoreCase(l.get(0))).count();
            project.setCount(count);
            projectList.add(project);
        });

        projectList.forEach(p -> {
            List<Theme> themeList = new ArrayList<>();
            apiMap.values().forEach(l -> {
                if (p.getName().equalsIgnoreCase(l.get(0))) {
                    Theme theme = new Theme();
                    theme.setName(l.get(1));
                    themeList.add(theme);
                }
            });
            List<Theme> themeDistinct = themeList.stream().distinct().collect(Collectors.toList());
            themeDistinct.forEach(t -> {
                List<Method> methodList = new ArrayList<>();
                Long count = themeList.stream().filter(th -> t.getName().equalsIgnoreCase(th.getName())).count();
                t.setCount(count);
                apiMap.values().forEach(l -> {
                    if (p.getName().equalsIgnoreCase(l.get(0)) && t.getName().equalsIgnoreCase(l.get(1))) {
                        Method method = new Method();
                        method.setName(l.get(2));
                        methodList.add(method);
                    }
                });
                List<Method> methodDistinct = methodList.stream().distinct().collect(Collectors.toList());
                methodDistinct.forEach(m -> {
                    Long coun = methodList.stream().filter(mt -> m.getName().equalsIgnoreCase(mt.getName())).count();
                    m.setCount(coun);
                });
                t.setMethod(methodDistinct);
            });
            p.setTheme(themeDistinct);
        });
        return populateResult(projectList);
    }

    private ApiAnalyze populateResult(List<Project> projectList) {
        ApiAnalyze result = new ApiAnalyze();
        Map<String, ApiAnalyze.Pro> proMap = new HashMap<>();
        projectList.forEach(p -> {
            ApiAnalyze.Pro pro = new ApiAnalyze.Pro();
            pro.set_count(p.getCount());
            Map<String, Object> themeMap = new HashMap<>();
            List<Theme> themeList = p.getTheme();
            themeList.forEach(t -> {
                Map<String, Long> methodMap = new HashMap<>();
                methodMap.put("_count", t.getCount());
                t.getMethod().forEach(m -> {
                    methodMap.put(m.getName(), m.getCount());
                });
                themeMap.put(t.getName(), methodMap);
            });
            pro.setTheme(themeMap);
            proMap.put(p.getName(), pro);
        });
        result.setProject(proMap);
        return result;
    }
}
