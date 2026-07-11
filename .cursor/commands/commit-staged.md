Commit only what is already staged (or stage only the files I name). Do not push unless I ask.

Follow the repo/user git commit protocol:

1. Review `git status`, `git diff --cached`, and recent `git log` style.
2. Draft a concise commit message focused on why.
3. Commit staged changes only (`git commit`, no blanket `git add .` unless I explicitly say to include unstaged/untracked).
4. Never update git config, never amend unless I ask, never skip hooks.
5. Show the resulting commit hash and `git status` briefly.
