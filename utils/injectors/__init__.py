"""Code injection utilities for adding comments and structure errors."""

from utils.injectors.comments import (
    inject_dead_code,
    inject_header_comment,
    inject_inline_comment,
)
from utils.injectors.structure_errors import (
    inject_class_name_mismatch,
    inject_debug_prints,
    inject_import_issues,
    inject_package_declaration,
)

__all__ = [
    # comments
    "inject_dead_code",
    "inject_header_comment",
    "inject_inline_comment",
    # structure_errors
    "inject_class_name_mismatch",
    "inject_debug_prints",
    "inject_import_issues",
    "inject_package_declaration",
]
