# AGENTS.md — mpvRx-zh 项目指南

## 项目概述

这是 [MpvRx](https://github.com/Riteshp2001/mpvRx) 的**中文汉化版本**。

- **上游仓库**：`https://github.com/Riteshp2001/mpvRx.git`（remote 名称：`upstream`）
- **远程仓库**：`https://github.com/BoxMiao007/mpvRx-zh.git`（remote 名称：`origin`）
- **applicationId**：`com.boxmiao.mpvrxzh`（独立于原版，可共存）
- **App 名称**：保持默认 "MpvRx"
- **原版状态**：已停止开发（v1.4.1-final 为最终版）

## 关键文件结构

```
app/src/main/res/
├── values/strings.xml              ← 默认语言（英文），保持不动
└── values-zh-rCN/strings.xml       ← 简体中文翻译（汉化核心文件）

app/build.gradle.kts                ← applicationId 在此配置
README.md                           ← 包含中文说明
```

## 汉化工作流

### 翻译规范

- **范围**：全量汉化（所有 UI 字符串）
- **风格**：使用"你"不用"您"；按钮文字用动词优先（"确认"而非"确认操作"）
- **术语统一**：
  - Player → 播放器
  - Playback → 播放
  - Subtitle → 字幕
  - Thumbnail → 缩略图
  - Playlist → 播放列表
  - Preferences → 偏好设置
  - Settings → 设置
  - Seek → 快进/快退
  - Pip → 画中画
  - AMOLED / Material You / HDR / SDR 不翻译

### 新增翻译流程

1. 上游可能新增字符串到 `values/strings.xml`
2. 需要同步更新 `values-zh-rCN/strings.xml`
3. 翻译文件中**只包含需要翻译的字符串**，`translatable="false"` 的条目不放入

### 提交规范

翻译提交使用统一格式：
```
i18n: 中文翻译 (批次N) - 简要说明
```

其他提交使用中文描述：
```
chore: 添加 GitNexus 代码智能配置
docs: 添加中文说明
fix: 修复xxx
feat: 新增xxx
```

## 上游同步策略

### 手动同步步骤

```bash
# 1. 获取上游最新代码
git fetch upstream

# 2. 查看上游新提交
git log --oneline master..upstream/master

# 3. 合并（几乎不会冲突，因为翻译在独立文件）
git merge upstream/master

# 4. 检查 strings.xml 变化
git diff HEAD~1 -- app/src/main/res/values/strings.xml

# 5. 补翻译
# 编辑 values-zh-rCN/strings.xml，添加新字符串的翻译

# 6. 提交翻译
git add app/src/main/res/values-zh-rCN/strings.xml
git commit -m "i18n: 中文翻译 - 同步上游新字符串"
```

### 自动监控

已配置 cron 任务（每周一 9:00）检查上游差异，有新字符串时通过微信通知。

任务 ID：`8d077b2a1e9e`

## 开发环境

- **语言**：Kotlin / Java
- **构建系统**：Gradle
- **最低 SDK**：查看 `app/build.gradle.kts`
- **目标 SDK**：查看 `app/build.gradle.kts`

## GitNexus 代码智能

本项目已索引（14014 符号，29582 关系，300 执行流）。

### 修改代码前必须

1. 运行 `impact()` 分析影响面
2. 如果风险为 HIGH 或 CRITICAL，必须警告用户
3. 提交前运行 `detect_changes()` 检查影响范围

### 常用查询

| 用途 | 工具 |
|------|------|
| 理解架构 | `query({query: "概念"})` |
| 影响面分析 | `impact({target: "符号名", direction: "upstream"})` |
| 上下文查看 | `context({name: "符号名"})` |
| 重命名 | `rename({symbol_name: "旧名", new_name: "新名"})` |

### 索引刷新

```bash
# 使用 Node 22 LTS（Node 24 与 tree-sitter 不兼容）
export PATH="$HOME/.local/n/bin:$PATH"
gitnexus analyze
```

## 注意事项

1. **不要修改 `values/strings.xml`**（英文原版），只改 `values-zh-rCN/strings.xml`
2. **不要修改 `translatable="false"` 的字符串**
3. **Android 资源文件中的 `%1$s`、`%d` 等占位符必须保留**
4. **XML 特殊字符需要转义**：`&` → `&amp;`，`<` → `&lt;`，`>` → `&gt;`
5. **plurals 标签需要处理单复数形式**（中文只有 one 和 other 两种）
