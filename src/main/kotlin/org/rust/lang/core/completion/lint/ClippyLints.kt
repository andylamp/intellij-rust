/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.core.completion.lint

val CLIPPY_LINTS: List<Lint> = listOf(
    Lint("cargo", true),
    Lint("complexity", true),
    Lint("correctness", true),
    Lint("deprecated", true),
    Lint("nursery", true),
    Lint("pedantic", true),
    Lint("perf", true),
    Lint("restriction", true),
    Lint("style", true),
    Lint("absurd_extreme_comparisons", false),
    Lint("almost_swapped", false),
    Lint("approx_constant", false),
    Lint("as_conversions", false),
    Lint("assertions_on_constants", false),
    Lint("assign_op_pattern", false),
    Lint("assign_ops", false),
    Lint("async_yields_async", false),
    Lint("await_holding_lock", false),
    Lint("await_holding_refcell_ref", false),
    Lint("bad_bit_mask", false),
    Lint("bind_instead_of_map", false),
    Lint("blacklisted_name", false),
    Lint("blanket_clippy_restriction_lints", false),
    Lint("blocks_in_if_conditions", false),
    Lint("bool_comparison", false),
    Lint("borrow_interior_mutable_const", false),
    Lint("borrowed_box", false),
    Lint("box_vec", false),
    Lint("boxed_local", false),
    Lint("builtin_type_shadow", false),
    Lint("bytes_nth", false),
    Lint("cargo_common_metadata", false),
    Lint("case_sensitive_file_extension_comparisons", false),
    Lint("cast_lossless", false),
    Lint("cast_possible_truncation", false),
    Lint("cast_possible_wrap", false),
    Lint("cast_precision_loss", false),
    Lint("cast_ptr_alignment", false),
    Lint("cast_ref_to_mut", false),
    Lint("cast_sign_loss", false),
    Lint("char_lit_as_u8", false),
    Lint("chars_last_cmp", false),
    Lint("chars_next_cmp", false),
    Lint("checked_conversions", false),
    Lint("clone_double_ref", false),
    Lint("clone_on_copy", false),
    Lint("clone_on_ref_ptr", false),
    Lint("cmp_nan", false),
    Lint("cmp_null", false),
    Lint("cmp_owned", false),
    Lint("cognitive_complexity", false),
    Lint("collapsible_else_if", false),
    Lint("collapsible_if", false),
    Lint("collapsible_match", false),
    Lint("comparison_chain", false),
    Lint("comparison_to_empty", false),
    Lint("copy_iterator", false),
    Lint("create_dir", false),
    Lint("crosspointer_transmute", false),
    Lint("dbg_macro", false),
    Lint("debug_assert_with_mut_call", false),
    Lint("decimal_literal_representation", false),
    Lint("declare_interior_mutable_const", false),
    Lint("default_numeric_fallback", false),
    Lint("default_trait_access", false),
    Lint("deprecated_cfg_attr", false),
    Lint("deprecated_semver", false),
    Lint("deref_addrof", false),
    Lint("derive_hash_xor_eq", false),
    Lint("derive_ord_xor_partial_ord", false),
    Lint("disallowed_method", false),
    Lint("diverging_sub_expression", false),
    Lint("doc_markdown", false),
    Lint("double_comparisons", false),
    Lint("double_must_use", false),
    Lint("double_neg", false),
    Lint("double_parens", false),
    Lint("drop_bounds", false),
    Lint("drop_copy", false),
    Lint("drop_ref", false),
    Lint("duplicate_underscore_argument", false),
    Lint("duration_subsec", false),
    Lint("else_if_without_else", false),
    Lint("empty_enum", false),
    Lint("empty_line_after_outer_attr", false),
    Lint("empty_loop", false),
    Lint("enum_clike_unportable_variant", false),
    Lint("enum_glob_use", false),
    Lint("enum_variant_names", false),
    Lint("eq_op", false),
    Lint("erasing_op", false),
    Lint("eval_order_dependence", false),
    Lint("excessive_precision", false),
    Lint("exhaustive_enums", false),
    Lint("exhaustive_structs", false),
    Lint("exit", false),
    Lint("expect_fun_call", false),
    Lint("expect_used", false),
    Lint("expl_impl_clone_on_copy", false),
    Lint("explicit_counter_loop", false),
    Lint("explicit_deref_methods", false),
    Lint("explicit_into_iter_loop", false),
    Lint("explicit_iter_loop", false),
    Lint("explicit_write", false),
    Lint("extend_from_slice", false),
    Lint("extra_unused_lifetimes", false),
    Lint("fallible_impl_from", false),
    Lint("field_reassign_with_default", false),
    Lint("filetype_is_file", false),
    Lint("filter_map", false),
    Lint("filter_map_identity", false),
    Lint("filter_map_next", false),
    Lint("filter_next", false),
    Lint("find_map", false),
    Lint("flat_map_identity", false),
    Lint("float_arithmetic", false),
    Lint("float_cmp", false),
    Lint("float_cmp_const", false),
    Lint("float_equality_without_abs", false),
    Lint("fn_address_comparisons", false),
    Lint("fn_params_excessive_bools", false),
    Lint("fn_to_numeric_cast", false),
    Lint("fn_to_numeric_cast_with_truncation", false),
    Lint("for_kv_map", false),
    Lint("for_loops_over_fallibles", false),
    Lint("forget_copy", false),
    Lint("forget_ref", false),
    Lint("from_iter_instead_of_collect", false),
    Lint("from_over_into", false),
    Lint("from_str_radix_10", false),
    Lint("future_not_send", false),
    Lint("get_last_with_len", false),
    Lint("get_unwrap", false),
    Lint("identity_op", false),
    Lint("if_let_mutex", false),
    Lint("if_let_redundant_pattern_matching", false),
    Lint("if_let_some_result", false),
    Lint("if_not_else", false),
    Lint("if_same_then_else", false),
    Lint("if_then_some_else_none", false),
    Lint("ifs_same_cond", false),
    Lint("implicit_clone", false),
    Lint("implicit_hasher", false),
    Lint("implicit_return", false),
    Lint("implicit_saturating_sub", false),
    Lint("imprecise_flops", false),
    Lint("inconsistent_digit_grouping", false),
    Lint("inconsistent_struct_constructor", false),
    Lint("indexing_slicing", false),
    Lint("ineffective_bit_mask", false),
    Lint("inefficient_to_string", false),
    Lint("infallible_destructuring_match", false),
    Lint("infinite_iter", false),
    Lint("inherent_to_string", false),
    Lint("inherent_to_string_shadow_display", false),
    Lint("inline_always", false),
    Lint("inline_asm_x86_att_syntax", false),
    Lint("inline_asm_x86_intel_syntax", false),
    Lint("inline_fn_without_body", false),
    Lint("inspect_for_each", false),
    Lint("int_plus_one", false),
    Lint("integer_arithmetic", false),
    Lint("integer_division", false),
    Lint("into_iter_on_array", false),
    Lint("into_iter_on_ref", false),
    Lint("invalid_atomic_ordering", false),
    Lint("invalid_ref", false),
    Lint("invalid_regex", false),
    Lint("invalid_upcast_comparisons", false),
    Lint("invisible_characters", false),
    Lint("items_after_statements", false),
    Lint("iter_cloned_collect", false),
    Lint("iter_count", false),
    Lint("iter_next_loop", false),
    Lint("iter_next_slice", false),
    Lint("iter_nth", false),
    Lint("iter_nth_zero", false),
    Lint("iter_skip_next", false),
    Lint("iterator_step_by_zero", false),
    Lint("just_underscores_and_digits", false),
    Lint("large_const_arrays", false),
    Lint("large_digit_groups", false),
    Lint("large_enum_variant", false),
    Lint("large_stack_arrays", false),
    Lint("large_types_passed_by_value", false),
    Lint("len_without_is_empty", false),
    Lint("len_zero", false),
    Lint("let_and_return", false),
    Lint("let_underscore_drop", false),
    Lint("let_underscore_lock", false),
    Lint("let_underscore_must_use", false),
    Lint("let_unit_value", false),
    Lint("linkedlist", false),
    Lint("logic_bug", false),
    Lint("lossy_float_literal", false),
    Lint("macro_use_imports", false),
    Lint("main_recursion", false),
    Lint("manual_async_fn", false),
    Lint("manual_filter_map", false),
    Lint("manual_find_map", false),
    Lint("manual_flatten", false),
    Lint("manual_map", false),
    Lint("manual_memcpy", false),
    Lint("manual_non_exhaustive", false),
    Lint("manual_ok_or", false),
    Lint("manual_range_contains", false),
    Lint("manual_saturating_arithmetic", false),
    Lint("manual_strip", false),
    Lint("manual_swap", false),
    Lint("manual_unwrap_or", false),
    Lint("many_single_char_names", false),
    Lint("map_clone", false),
    Lint("map_collect_result_unit", false),
    Lint("map_entry", false),
    Lint("map_err_ignore", false),
    Lint("map_flatten", false),
    Lint("map_identity", false),
    Lint("map_unwrap_or", false),
    Lint("match_as_ref", false),
    Lint("match_bool", false),
    Lint("match_like_matches_macro", false),
    Lint("match_on_vec_items", false),
    Lint("match_overlapping_arm", false),
    Lint("match_ref_pats", false),
    Lint("match_same_arms", false),
    Lint("match_single_binding", false),
    Lint("match_wild_err_arm", false),
    Lint("match_wildcard_for_single_variants", false),
    Lint("maybe_infinite_iter", false),
    Lint("mem_discriminant_non_enum", false),
    Lint("mem_forget", false),
    Lint("mem_replace_option_with_none", false),
    Lint("mem_replace_with_default", false),
    Lint("mem_replace_with_uninit", false),
    Lint("min_max", false),
    Lint("misaligned_transmute", false),
    Lint("mismatched_target_os", false),
    Lint("misrefactored_assign_op", false),
    Lint("missing_const_for_fn", false),
    Lint("missing_docs_in_private_items", false),
    Lint("missing_errors_doc", false),
    Lint("missing_inline_in_public_items", false),
    Lint("missing_panics_doc", false),
    Lint("missing_safety_doc", false),
    Lint("mistyped_literal_suffixes", false),
    Lint("mixed_case_hex_literals", false),
    Lint("module_inception", false),
    Lint("module_name_repetitions", false),
    Lint("modulo_arithmetic", false),
    Lint("modulo_one", false),
    Lint("multiple_crate_versions", false),
    Lint("multiple_inherent_impl", false),
    Lint("must_use_candidate", false),
    Lint("must_use_unit", false),
    Lint("mut_from_ref", false),
    Lint("mut_mut", false),
    Lint("mut_mutex_lock", false),
    Lint("mut_range_bound", false),
    Lint("mutable_key_type", false),
    Lint("mutex_atomic", false),
    Lint("mutex_integer", false),
    Lint("naive_bytecount", false),
    Lint("needless_arbitrary_self_type", false),
    Lint("needless_bool", false),
    Lint("needless_borrow", false),
    Lint("needless_borrowed_reference", false),
    Lint("needless_collect", false),
    Lint("needless_continue", false),
    Lint("needless_doctest_main", false),
    Lint("needless_lifetimes", false),
    Lint("needless_pass_by_value", false),
    Lint("needless_question_mark", false),
    Lint("needless_range_loop", false),
    Lint("needless_return", false),
    Lint("needless_update", false),
    Lint("neg_cmp_op_on_partial_ord", false),
    Lint("neg_multiply", false),
    Lint("never_loop", false),
    Lint("new_ret_no_self", false),
    Lint("new_without_default", false),
    Lint("no_effect", false),
    Lint("non_ascii_literal", false),
    Lint("nonminimal_bool", false),
    Lint("nonsensical_open_options", false),
    Lint("not_unsafe_ptr_arg_deref", false),
    Lint("ok_expect", false),
    Lint("op_ref", false),
    Lint("option_as_ref_deref", false),
    Lint("option_env_unwrap", false),
    Lint("option_if_let_else", false),
    Lint("option_map_or_none", false),
    Lint("option_map_unit_fn", false),
    Lint("option_option", false),
    Lint("or_fun_call", false),
    Lint("out_of_bounds_indexing", false),
    Lint("overflow_check_conditional", false),
    Lint("panic", false),
    Lint("panic_in_result_fn", false),
    Lint("panic_params", false),
    Lint("panicking_unwrap", false),
    Lint("partialeq_ne_impl", false),
    Lint("path_buf_push_overwrite", false),
    Lint("pattern_type_mismatch", false),
    Lint("possible_missing_comma", false),
    Lint("precedence", false),
    Lint("print_literal", false),
    Lint("print_stderr", false),
    Lint("print_stdout", false),
    Lint("print_with_newline", false),
    Lint("println_empty_string", false),
    Lint("ptr_arg", false),
    Lint("ptr_as_ptr", false),
    Lint("ptr_eq", false),
    Lint("ptr_offset_with_cast", false),
    Lint("pub_enum_variant_names", false),
    Lint("question_mark", false),
    Lint("range_minus_one", false),
    Lint("range_plus_one", false),
    Lint("range_step_by_zero", false),
    Lint("range_zip_with_len", false),
    Lint("rc_buffer", false),
    Lint("redundant_allocation", false),
    Lint("redundant_clone", false),
    Lint("redundant_closure", false),
    Lint("redundant_closure_call", false),
    Lint("redundant_closure_for_method_calls", false),
    Lint("redundant_else", false),
    Lint("redundant_field_names", false),
    Lint("redundant_pattern", false),
    Lint("redundant_pattern_matching", false),
    Lint("redundant_pub_crate", false),
    Lint("redundant_slicing", false),
    Lint("redundant_static_lifetimes", false),
    Lint("ref_in_deref", false),
    Lint("ref_option_ref", false),
    Lint("regex_macro", false),
    Lint("repeat_once", false),
    Lint("replace_consts", false),
    Lint("rest_pat_in_fully_bound_structs", false),
    Lint("result_map_or_into_option", false),
    Lint("result_map_unit_fn", false),
    Lint("result_unit_err", false),
    Lint("reversed_empty_ranges", false),
    Lint("same_functions_in_if_condition", false),
    Lint("same_item_push", false),
    Lint("search_is_some", false),
    Lint("self_assignment", false),
    Lint("semicolon_if_nothing_returned", false),
    Lint("serde_api_misuse", false),
    Lint("shadow_reuse", false),
    Lint("shadow_same", false),
    Lint("shadow_unrelated", false),
    Lint("short_circuit_statement", false),
    Lint("should_assert_eq", false),
    Lint("should_implement_trait", false),
    Lint("similar_names", false),
    Lint("single_char_add_str", false),
    Lint("single_char_pattern", false),
    Lint("single_component_path_imports", false),
    Lint("single_element_loop", false),
    Lint("single_match", false),
    Lint("single_match_else", false),
    Lint("size_of_in_element_count", false),
    Lint("skip_while_next", false),
    Lint("slow_vector_initialization", false),
    Lint("stable_sort_primitive", false),
    Lint("str_to_string", false),
    Lint("string_add", false),
    Lint("string_add_assign", false),
    Lint("string_extend_chars", false),
    Lint("string_from_utf8_as_bytes", false),
    Lint("string_lit_as_bytes", false),
    Lint("string_to_string", false),
    Lint("struct_excessive_bools", false),
    Lint("suboptimal_flops", false),
    Lint("suspicious_arithmetic_impl", false),
    Lint("suspicious_assignment_formatting", false),
    Lint("suspicious_else_formatting", false),
    Lint("suspicious_map", false),
    Lint("suspicious_op_assign_impl", false),
    Lint("suspicious_operation_groupings", false),
    Lint("suspicious_unary_op_formatting", false),
    Lint("tabs_in_doc_comments", false),
    Lint("temporary_assignment", false),
    Lint("temporary_cstring_as_ptr", false),
    Lint("to_digit_is_some", false),
    Lint("to_string_in_display", false),
    Lint("todo", false),
    Lint("too_many_arguments", false),
    Lint("too_many_lines", false),
    Lint("toplevel_ref_arg", false),
    Lint("trait_duplication_in_bounds", false),
    Lint("transmute_bytes_to_str", false),
    Lint("transmute_float_to_int", false),
    Lint("transmute_int_to_bool", false),
    Lint("transmute_int_to_char", false),
    Lint("transmute_int_to_float", false),
    Lint("transmute_ptr_to_ptr", false),
    Lint("transmute_ptr_to_ref", false),
    Lint("transmutes_expressible_as_ptr_casts", false),
    Lint("transmuting_null", false),
    Lint("trivial_regex", false),
    Lint("trivially_copy_pass_by_ref", false),
    Lint("try_err", false),
    Lint("type_complexity", false),
    Lint("type_repetition_in_bounds", false),
    Lint("undropped_manually_drops", false),
    Lint("unicode_not_nfc", false),
    Lint("unimplemented", false),
    Lint("uninit_assumed_init", false),
    Lint("unit_arg", false),
    Lint("unit_cmp", false),
    Lint("unit_return_expecting_ord", false),
    Lint("unknown_clippy_lints", false),
    Lint("unnecessary_cast", false),
    Lint("unnecessary_filter_map", false),
    Lint("unnecessary_fold", false),
    Lint("unnecessary_lazy_evaluations", false),
    Lint("unnecessary_mut_passed", false),
    Lint("unnecessary_operation", false),
    Lint("unnecessary_sort_by", false),
    Lint("unnecessary_unwrap", false),
    Lint("unnecessary_wraps", false),
    Lint("unneeded_field_pattern", false),
    Lint("unneeded_wildcard_pattern", false),
    Lint("unnested_or_patterns", false),
    Lint("unreachable", false),
    Lint("unreadable_literal", false),
    Lint("unsafe_derive_deserialize", false),
    Lint("unsafe_removed_from_name", false),
    Lint("unsafe_vector_initialization", false),
    Lint("unseparated_literal_suffix", false),
    Lint("unsound_collection_transmute", false),
    Lint("unstable_as_mut_slice", false),
    Lint("unstable_as_slice", false),
    Lint("unused_collect", false),
    Lint("unused_io_amount", false),
    Lint("unused_label", false),
    Lint("unused_self", false),
    Lint("unused_unit", false),
    Lint("unusual_byte_groupings", false),
    Lint("unwrap_in_result", false),
    Lint("unwrap_used", false),
    Lint("upper_case_acronyms", false),
    Lint("use_debug", false),
    Lint("use_self", false),
    Lint("used_underscore_binding", false),
    Lint("useless_asref", false),
    Lint("useless_attribute", false),
    Lint("useless_conversion", false),
    Lint("useless_format", false),
    Lint("useless_let_if_seq", false),
    Lint("useless_transmute", false),
    Lint("useless_vec", false),
    Lint("vec_box", false),
    Lint("vec_init_then_push", false),
    Lint("vec_resize_to_zero", false),
    Lint("verbose_bit_mask", false),
    Lint("verbose_file_reads", false),
    Lint("vtable_address_comparisons", false),
    Lint("while_immutable_condition", false),
    Lint("while_let_loop", false),
    Lint("while_let_on_iterator", false),
    Lint("wildcard_dependencies", false),
    Lint("wildcard_enum_match_arm", false),
    Lint("wildcard_imports", false),
    Lint("wildcard_in_or_patterns", false),
    Lint("write_literal", false),
    Lint("write_with_newline", false),
    Lint("writeln_empty_string", false),
    Lint("wrong_pub_self_convention", false),
    Lint("wrong_self_convention", false),
    Lint("wrong_transmute", false),
    Lint("zero_divided_by_zero", false),
    Lint("zero_prefixed_literal", false),
    Lint("zero_ptr", false),
    Lint("zero_sized_map_values", false),
    Lint("zst_offset", false)
)
