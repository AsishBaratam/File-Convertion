package com.jocata.validators;

import java.util.List;

public class Validator {

        public static boolean validateSources(List<String> sources) {
            // Check if the list is null or empty
            if (sources == null || sources.isEmpty()) {
                return false;
            }

            // Check if any source string is null or empty
            for (String source : sources) {
                if (source == null || source.isEmpty()) {
                    return false;
                }
            }

            return true;
        }

        public static boolean validateTarget(String target) {
            // Check if the target string is null or empty
            return target != null && !target.isEmpty();
        }
    }


