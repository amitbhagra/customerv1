## GitHub Copilot Configuration

### Code Modification Guidelines
When making modifications to this Spring Boot project, please follow these conventions to ensure proper tracking and documentation of changes.

### JIRA ID Extraction
- Always extract the JIRA ID from the currently active Git branch using Git repository metadata.
- Branch naming pattern: `feature/[JIRA-ID]` or `bugfix/[JIRA-ID]` or similar
- Example: From branch `feature/CODE-19048`, extract `CODE-19048`
- If no JIRA ID is found, use `NO-JIRA`

### Modification Markers
All code changes must be wrapped with the following markers:

- **Start Marker:** `// COPILOT MODIFICATION START - [JIRA-ID]: [Description]`
- **End Marker:** `// COPILOT MODIFICATION END - [JIRA-ID]`

---

## Requirements
- Include clear descriptions of what was changed in the start marker
- Mark **ALL** code changes including additions, modifications, deletions, and refactoring
- Use the exact JIRA ID extracted from the current branch name
- Be consistent with the JIRA ID in both start and end markers

### Example Format
```java
// COPILOT MODIFICATION START - CODE-19048: Added input validation for generateReleaseNotesPage
if (project == null || project.trim().isEmpty()) {
    throw new IllegalArgumentException("Project parameter cannot be null or empty");
}
// COPILOT MODIFICATION END - CODE-19048
```

